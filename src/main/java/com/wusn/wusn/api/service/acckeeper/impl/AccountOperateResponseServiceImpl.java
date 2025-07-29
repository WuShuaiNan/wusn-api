package com.wusn.wusn.api.service.acckeeper.impl;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.wusn.api.service.acckeeper.AccountOperateResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class AccountOperateResponseServiceImpl implements AccountOperateResponseService {

    @DubboReference(group = "${dubbo.acckeeper.group}")
    private AccountOperateService service;

    @DubboReference(group = "${dubbo.acckeeper.group}")
    private AccountMaintainService accountMaintainService;

    @Override
    public void register(AccountRegisterInfo accountRegisterInfo) throws ServiceException {
        service.register(accountRegisterInfo);
    }

    @Override
    public void update(AccountUpdateInfo accountUpdateInfo) throws ServiceException {
        service.update(accountUpdateInfo);
    }

    @Override
    public boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws ServiceException {
        return service.checkPassword(passwordCheckInfo);
    }

    @Override
    public void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws ServiceException {
        service.updatePassword(passwordUpdateInfo);
    }

    @Override
    public void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException {
        service.resetPassword(passwordResetInfo);
    }

    @Override
    public void invalid(StringIdKey accountKey) throws ServiceException {
        service.invalid(accountKey);
    }

    @Override
    public PagedData<Account> lookup(PagingInfo pagingInfo) throws ServiceException {
        return accountMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Account> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException {
        return accountMaintainService.lookup(preset, objs, pagingInfo);
    }

}
