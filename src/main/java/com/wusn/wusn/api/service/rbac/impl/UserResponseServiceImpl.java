package com.wusn.wusn.api.service.rbac.impl;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.service.rbac.UserResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class UserResponseServiceImpl implements UserResponseService {

    @DubboReference(group = "${dubbo.rbac.group}")
    private UserMaintainService service;
    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public User get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(User user) throws ServiceException {
        return service.insert(user);
    }

    @Override
    public void update(User user) throws ServiceException {
        service.update(user);
    }

    @Override
    public void addRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException {
        service.addRoleRelation(userIdKey, roleIdKey);
    }

    @Override
    public void deleteRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException {
        service.deleteRoleRelation(userIdKey, roleIdKey);
    }

    @Override
    public PagedData<User> lookup(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<User> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(preset, objs, pagingInfo);
    }
}
