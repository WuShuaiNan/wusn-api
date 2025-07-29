package com.wusn.wusn.api.controller.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputPermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.annotation.LoginValidated;
import com.wusn.wusn.api.config.beanTransform.RbacMapper;
import com.wusn.wusn.api.service.rbac.PermissionGroupResponseService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller 权限组
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/rbac")
@CrossOrigin
public class PermissionGroupController {

    @Autowired
    private PermissionGroupResponseService service;

    RbacMapper beanTransform = Mappers.getMapper(RbacMapper.class);

    @GetMapping("/permission-group/{id}/exists")
    @LoginValidated
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("id") String id
    ) {
        try {
            boolean exists = service.exists(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/permission-group/{id}/get")
    @LoginValidated
    public FastJsonResponseData<FastJsonPermissionGroup> get(
            HttpServletRequest request,
            @PathVariable("id") String id
    ) {
        try {
            PermissionGroup permissionGroup = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermissionGroup.of(permissionGroup)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/permission-group/insert")
    @LoginValidated
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody WebInputPermissionGroup permissionGroup
    ) {
        try {
            StringIdKey key = service.insert(WebInputPermissionGroup.toStackBean(permissionGroup));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(key)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/permission-group/update")
    @LoginValidated
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputPermissionGroup permissionGroup
    ) {
        try {
            service.update(WebInputPermissionGroup.toStackBean(permissionGroup));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/permission-group/{id}/delete")
    @LoginValidated
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("id") String id
    ) {
        try {
            service.delete(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/permission-group/lookup")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermissionGroup>> lookup(
            HttpServletRequest request,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<PermissionGroup> all = service.lookup(new PagingInfo(page, rows));
            PagedData<FastJsonPermissionGroup> transform = beanTransform.permissionGroup2JSFixedFastJsonPermissionGroup(all);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

}
