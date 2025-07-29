package com.wusn.wusn.api.service.rbac.impl;

import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.service.rbac.PexpResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PexpResponseServiceImpl implements PexpResponseService {

    @DubboReference
    private PexpMaintainService service;
    
    @DubboReference
    private LongIdService longIdService;

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public Pexp get(LongIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public LongIdKey insert(Pexp pexp) throws ServiceException {
        return service.insert(pexp);
    }

    @Override
    public void update(Pexp pexp) throws ServiceException {
        service.update(pexp);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public void reset(String roleId, List<String> newPermissions) throws ServiceException {
        // 1.查询出角色所有的权限
        List<Pexp> pexps = service.lookup(
                PexpMaintainService.PEXP_FOR_ROLE,
                new Object[]{ new StringIdKey(roleId) }
        ).getData();
        // 2.删除角色之前的权限
        if (pexps.size() != 0) {
            for (Pexp pexp : pexps) {
                service.delete(pexp.getKey());
            }   
        }
        // 3.重设角色的新权限
        for (String permission : newPermissions) {
            Pexp pexp = new Pexp();
            pexp.setKey(longIdService.nextLongIdKey());
            pexp.setContent("+exact@" + permission);
            pexp.setRoleKey(new StringIdKey(roleId));
            pexp.setRemark("由前端创建、修改");
            service.insert(pexp);
        }
    }

    @Override
    public PagedData<Pexp> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(preset, objs, pagingInfo);
    }
}
