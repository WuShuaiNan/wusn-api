package com.wusn.wusn.api.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.List;

/**
 * Response 角色响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface RoleResponseService {

    boolean exists(StringIdKey key) throws ServiceException;

    Role get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Role role) throws ServiceException;

    void update(Role role) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Role> lookup(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Role> lookup(String preset, Object[] objs) throws ServiceException;

    PagedData<Role> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException;
    
    void relation(StringIdKey userIdKey, List<StringIdKey> newRoleIdKeys) throws ServiceException;

}
