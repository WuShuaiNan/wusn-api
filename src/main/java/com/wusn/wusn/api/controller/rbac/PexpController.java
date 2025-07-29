package com.wusn.wusn.api.controller.rbac;

import com.dwarfeng.rbacds.sdk.bean.entity.JSFixedFastJsonPexp;
import com.dwarfeng.rbacds.sdk.bean.entity.WebInputPexp;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.annotation.LoginValidated;
import com.wusn.wusn.api.config.beanTransform.RbacMapper;
import com.wusn.wusn.api.service.rbac.PexpResponseService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller 授权
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/rbac")
@CrossOrigin
public class PexpController {

    @Autowired
    private PexpResponseService service;

    RbacMapper beanTransform = Mappers.getMapper(RbacMapper.class);

    @GetMapping("/pexp/{id}/exists")
    @LoginValidated
    public FastJsonResponseData<Boolean> exists(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            boolean exists = service.exists(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(exists));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/pexp/{id}/get")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPexp> get(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            Pexp pexp = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPexp.of(pexp)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/pexp/insert")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody WebInputPexp pexp
    ) {
        try {
            LongIdKey key = service.insert(WebInputPexp.toStackBean(pexp));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(key)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/pexp/update")
    @LoginValidated
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputPexp pexp
    ) {
        try {
            service.update(WebInputPexp.toStackBean(pexp));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/pexp/{id}/delete")
    @LoginValidated
    public FastJsonResponseData<Object> delete(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            service.delete(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/pexp/{id}/reset")
    @LoginValidated
    public FastJsonResponseData<Object> reset(
            HttpServletRequest request,
            @PathVariable("id") String roleId,
            @RequestBody List<String> newPermissions
    ) {
        try {
            service.reset(roleId, newPermissions);
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/pexp/{role-id}/lookup")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonPexp>> childForRole(
            HttpServletRequest request,
            @PathVariable("role-id") String roleId,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Pexp> all = service.lookup(
                    PexpMaintainService.PEXP_FOR_ROLE,
                    new Object[] { new StringIdKey(roleId)},
                    new PagingInfo(page, rows)
            );
            PagedData<JSFixedFastJsonPexp> transform = beanTransform.pexp2JSFixedFastJsonPexp(all);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

}
