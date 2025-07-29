package com.wusn.wusn.api.controller.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.dto.*;
import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonAccount;
import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonLoginState;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputStringIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.annotation.LoginValidated;
import com.wusn.wusn.api.config.beanTransform.AcckeeperMapper;
import com.wusn.wusn.api.service.acckeeper.AccountOperateResponseService;
import com.wusn.wusn.api.service.acckeeper.LoginResponseService;
import com.wusn.wusn.api.service.rbac.UserResponseService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller 账户
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/acckeeper")
@CrossOrigin
public class AccountController {

    @Autowired
    private LoginResponseService loginResponseService;

    @Autowired
    private AccountOperateResponseService accountOperateResponseService;

    @Autowired
    UserResponseService userResponseService;

    AcckeeperMapper beanTransform = Mappers.getMapper(AcckeeperMapper.class);

    @PostMapping("/account/register")
    @LoginValidated
    public FastJsonResponseData<Object> register(
            HttpServletRequest request,
            @RequestBody WebInputAccountRegisterInfo accountRegisterInfo
    ) {
        try {
            accountOperateResponseService.register(WebInputAccountRegisterInfo.toStackBean(accountRegisterInfo));
            userResponseService.insert(new User(WebInputStringIdKey.toStackBean(accountRegisterInfo.getAccountKey()), "由前端创建"));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/update")
    @LoginValidated
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputAccountUpdateInfo accountUpdateInfo
    ) {
        try {
            accountOperateResponseService.update(WebInputAccountUpdateInfo.toStackBean(accountUpdateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/update-password")
    @LoginValidated
    public FastJsonResponseData<Object> updatePassword(
            HttpServletRequest request,
            @RequestBody WebInputPasswordUpdateInfo passwordUpdateInfo
    ) {
        try {
            accountOperateResponseService.updatePassword(WebInputPasswordUpdateInfo.toStackBean(passwordUpdateInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/reset-password")
    @LoginValidated
    public FastJsonResponseData<Object> resetPassword(
            HttpServletRequest request,
            @RequestBody WebInputPasswordResetInfo passwordResetInfo
    ) {
        try {
            accountOperateResponseService.resetPassword(WebInputPasswordResetInfo.toStackBean(passwordResetInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/isLogin")
    public FastJsonResponseData<Boolean> isLogin(
            HttpServletRequest request,
            @RequestBody WebInputLongIdKey loginStateKey
    ) {
        try {
            boolean result = loginResponseService.isLogin(WebInputLongIdKey.toStackBean(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(result));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/login")
    public FastJsonResponseData<JSFixedFastJsonLoginState> login(
            HttpServletRequest request,
            @RequestBody WebInputLoginInfo loginInfo
    ) {
        try {
            LoginState loginState = loginResponseService.login(WebInputLoginInfo.toStackBean(loginInfo));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLoginState.of(loginState)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/logout")
    public FastJsonResponseData<Object> logout(
            HttpServletRequest request,
            @RequestBody WebInputLongIdKey loginStateKey
    ) {
        try {
            loginResponseService.logout(WebInputLongIdKey.toStackBean(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/account/postpone")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonLoginState> postpone(
            HttpServletRequest request,
            @RequestBody WebInputLongIdKey loginStateKey
    ) {
        try {
            LoginState loginState = loginResponseService.postpone(WebInputLongIdKey.toStackBean(loginStateKey));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLoginState.of(loginState)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/account/lookup")
    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonAccount>> lookup(
            HttpServletRequest request,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Account> all = accountOperateResponseService.lookup(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonAccount> transform = beanTransform.account2JSFixedFastJsonAccount(all);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

}
