package com.wusn.wusn.api.service.rbac.impl;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginService;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionLookupService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.service.rbac.PermissionResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionResponseServiceImpl implements PermissionResponseService {

    @DubboReference(group = "${dubbo.rbac.group}")
    private PermissionMaintainService service;

    @DubboReference(group = "${dubbo.acckeeper.group}")
    private LoginService loginService;

    @DubboReference(group = "${dubbo.rbac.group}")
    private PermissionLookupService permissionLookupService;

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public Permission get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(Permission permission) throws ServiceException {
        return service.insert(permission);
    }

    @Override
    public void update(Permission permission) throws ServiceException {
        service.update(permission);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<Permission> lookup(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<Permission> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(preset, objs, pagingInfo);
    }

    @Override
    public List<String> getPermissions(LongIdKey longIdKey) throws ServiceException {
        LoginState loginState = loginService.getLoginState(longIdKey);
        List<Permission> permissions = permissionLookupService.lookupForUser(loginState.getAccountKey());
        return permissions.stream().map(p -> p.getKey().getStringId()).collect(Collectors.toList());
    }

    @Override
    public List<Permission> lookupForRole(StringIdKey roleKey) throws ServiceException {
        return permissionLookupService.lookupForRole(roleKey);
    }
}
