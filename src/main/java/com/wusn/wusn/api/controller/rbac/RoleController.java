package com.wusn.wusn.api.controller.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputRole;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
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
import com.wusn.wusn.api.service.rbac.RoleResponseService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller 角色
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/rbac")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleResponseService service;

    RbacMapper beanTransform = Mappers.getMapper(RbacMapper.class);

    @GetMapping("/role/{id}/exists")
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

    @GetMapping("/role/{id}/get")
    @LoginValidated
    public FastJsonResponseData<FastJsonRole> get(
            HttpServletRequest request,
            @PathVariable("id") String id
    ) {
        try {
            Role role = service.get(new StringIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonRole.of(role)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/role/insert")
    @LoginValidated
    public FastJsonResponseData<FastJsonStringIdKey> insert(
            HttpServletRequest request,
            @RequestBody WebInputRole role
    ) {
        try {
            StringIdKey key = service.insert(WebInputRole.toStackBean(role));
            return FastJsonResponseData.of(ResponseDataUtil.good(FastJsonStringIdKey.of(key)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/role/update")
    @LoginValidated
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputRole role
    ) {
        try {
            service.update(WebInputRole.toStackBean(role));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/role/{id}/delete")
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

    @GetMapping("/role/lookup")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPagedData<FastJsonRole>> lookup(
            HttpServletRequest request,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Role> all = service.lookup(new PagingInfo(page, rows));
            PagedData<FastJsonRole> transform = beanTransform.role2FastJsonRole(all);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/role/{id}/child-for-user")
    @LoginValidated
    public FastJsonResponseData<List<String>> childForUser(
            HttpServletRequest request,
            @PathVariable("id") String id
    ) {
        try {
            List<String> roles = service.lookup(
                    RoleMaintainService.ROLE_FOR_USER,
                    new Object[]{new StringIdKey(id)}
            ).getData().stream().map(Role::getKey).map(StringIdKey::getStringId).collect(Collectors.toList());
            return FastJsonResponseData.of(ResponseDataUtil.good(roles));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/role/{id}/relation")
    @LoginValidated
    public FastJsonResponseData<Object> relation(
            HttpServletRequest request,
            @PathVariable("id") String userId,
            @RequestBody List<String> roles
    ) {
        try {
            List<StringIdKey> newRoleIdKeys = new ArrayList<>();
            for (String role : roles) {
                newRoleIdKeys.add(new StringIdKey(role));
            }
            service.relation(new StringIdKey(userId), newRoleIdKeys);
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

}
