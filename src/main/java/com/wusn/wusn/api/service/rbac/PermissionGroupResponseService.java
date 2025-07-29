package com.wusn.wusn.api.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * Response 权限组响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface PermissionGroupResponseService {

    boolean exists(StringIdKey key) throws ServiceException;

    PermissionGroup get(StringIdKey key) throws ServiceException;

    StringIdKey insert(PermissionGroup permissionGroup) throws ServiceException;

    void update(PermissionGroup permissionGroup) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<PermissionGroup> lookup(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PermissionGroup> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException;

}
