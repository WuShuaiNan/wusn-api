package com.wusn.wusn.api.service.rbac.impl;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.service.rbac.RoleResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleResponseServiceImpl implements RoleResponseService {

    @DubboReference(group = "${dubbo.rbac.group}")
    private RoleMaintainService service;

    @DubboReference(group = "${dubbo.rbac.group}")
    private UserMaintainService userMaintainService;

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public Role get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(Role role) throws ServiceException {
        return service.insert(role);
    }

    @Override
    public void update(Role role) throws ServiceException {
        service.update(role);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<Role> lookup(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<Role> lookup(String preset, Object[] objs) throws ServiceException {
        return service.lookup(preset, objs);
    }

    @Override
    public PagedData<Role> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(preset, objs, pagingInfo);
    }

    @Override
    public void relation(StringIdKey userIdKey, List<StringIdKey> newRoleIdKeys) throws ServiceException {
        // 1. 查询出之前的关联
        List<StringIdKey> oldRoleIdKeys = service.lookupAsList(
                RoleMaintainService.ROLE_FOR_USER,
                new Object[] { userIdKey }
        ).stream().map(Role::getKey).collect(Collectors.toList());
        // 2. 删除已有关联
        userMaintainService.batchDeleteRoleRelations(userIdKey, oldRoleIdKeys);
        // 3. 添加新的关联
        userMaintainService.batchAddRoleRelations(userIdKey, newRoleIdKeys);
    }
}
