package com.wusn.wusn.api.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.List;

/**
 * Response 权限响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface PermissionResponseService {

    boolean exists(StringIdKey key) throws ServiceException;

    Permission get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Permission permission) throws ServiceException;

    void update(Permission permission) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Permission> lookup(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException;

    List<String> getPermissions(LongIdKey longIdKey) throws ServiceException;
    
    List<Permission> lookupForRole(StringIdKey roleKey) throws ServiceException;

}
