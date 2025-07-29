package com.wusn.wusn.api.controller.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputPermission;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.annotation.LoginValidated;
import com.wusn.wusn.api.config.beanTransform.RbacMapper;
import com.wusn.wusn.api.service.rbac.PermissionResponseService;
import com.wusn.wusn.api.utils.TokenUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller 权限
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/rbac")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionResponseService service;

    private TokenUtil tokenUtil;

    RbacMapper beanTransform = Mappers.getMapper(RbacMapper.class);

    @GetMapping("/permission/{id}/exists")
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

    @GetMapping("/permission/{id}/get")
    @LoginValidated
    public FastJsonResponseData<FastJsonPermission> get(
            HttpServletRequest request,
            @PathVariable("id") String id
    ) {
        try {
            Permission permission = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonPermission.of(permission)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/permission/insert")
    @LoginValidated
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody WebInputPermission permission
    ) {
        try {
            StringIdKey key = service.insert(WebInputPermission.toStackBean(permission));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(key)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/permission/update")
    @LoginValidated
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputPermission permission
    ) {
        try {
            service.update(WebInputPermission.toStackBean(permission));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/permission/{id}/delete")
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

    @GetMapping("/permission/lookup")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonPermission>> lookup(
            HttpServletRequest request,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Permission> all = service.lookup(new PagingInfo(page, rows));
            PagedData<FastJsonPermission> transform = beanTransform.permission2JSFixedFastJsonPermission(all);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/permission/my-permissions")
    @LoginValidated
    public FastJsonResponseData<List<String>> myPermission(HttpServletRequest request) {
        try {
            long tokenId = tokenUtil.getTokenId(request);
            List<String> permissions = service.getPermissions(new LongIdKey(tokenId));
            return FastJsonResponseData.of(ResponseDataUtil.good(permissions));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/permission/{roleId}/lookup-for-role")
    @LoginValidated
    public FastJsonResponseData<List<String>> lookupForRole(
            HttpServletRequest request,
            @PathVariable("roleId") String roleId
    ) {
        try {
            List<Permission> permissions = service.lookupForRole(new StringIdKey(roleId));
            List<String> results = new ArrayList<>();
            for (Permission permission : permissions) {
                results.add(permission.getKey().getStringId());
            }
            return FastJsonResponseData.of(ResponseDataUtil.good(results));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }


}
