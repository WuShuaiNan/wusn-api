package com.wusn.wusn.api.service.rbac.impl;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.service.rbac.PermissionGroupResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class PermissionGroupResponseServiceImpl implements PermissionGroupResponseService {

    @DubboReference(group = "${dubbo.wusn.video.group}")
    private PermissionGroupMaintainService service;

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public PermissionGroup get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(PermissionGroup permissionGroup) throws ServiceException {
        return service.insert(permissionGroup);
    }

    @Override
    public void update(PermissionGroup permissionGroup) throws ServiceException {
        service.update(permissionGroup);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<PermissionGroup> lookup(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<PermissionGroup> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(preset, objs, pagingInfo);
    }
}
