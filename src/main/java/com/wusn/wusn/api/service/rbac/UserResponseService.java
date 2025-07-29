package com.wusn.wusn.api.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * Response 用户响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface UserResponseService {

    boolean exists(StringIdKey key) throws ServiceException;

    User get(StringIdKey key) throws ServiceException;

    StringIdKey insert(User user) throws ServiceException;

    void update(User user) throws ServiceException;

    void addRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException;

    void deleteRoleRelation(StringIdKey userIdKey, StringIdKey roleIdKey) throws ServiceException;

    PagedData<User> lookup(PagingInfo pagingInfo) throws ServiceException;

    PagedData<User> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException;

}
