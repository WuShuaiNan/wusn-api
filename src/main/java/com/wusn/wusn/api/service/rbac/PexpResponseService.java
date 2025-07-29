package com.wusn.wusn.api.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.List;

/**
 * Response 授权响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface PexpResponseService {

    boolean exists(LongIdKey key) throws ServiceException;

    Pexp get(LongIdKey key) throws ServiceException;

    LongIdKey insert(Pexp pexp) throws ServiceException;

    void update(Pexp pexp) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;
    
    void reset(String roleId ,List<String> newPermissions) throws ServiceException;

    PagedData<Pexp> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException;

}
