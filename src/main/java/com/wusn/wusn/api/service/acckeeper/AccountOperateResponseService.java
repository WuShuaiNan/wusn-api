package com.wusn.wusn.api.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * Response 账户操作响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface AccountOperateResponseService {

    /**
     * 注册账户。
     *
     * @param accountRegisterInfo 账户注册信息。
     */
    void register(AccountRegisterInfo accountRegisterInfo) throws ServiceException;

    /**
     * 更新账户。
     *
     * @param accountUpdateInfo 账户更新信息。
     */
    void update(AccountUpdateInfo accountUpdateInfo) throws ServiceException;

    /**
     * 判断指定的账户密码是否正确。
     *
     * @param passwordCheckInfo 密码检查信息。
     * @return 账户的密码是否正确。
     */
    boolean checkPassword(PasswordCheckInfo passwordCheckInfo) throws ServiceException;

    /**
     * 更新账户密码。
     *
     * @param passwordUpdateInfo 密码更新信息。
     */
    void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws ServiceException;

    /**
     * 重置账户密码。
     *
     * @param passwordResetInfo 密码重置信息。
     */
    void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException;

    /**
     * 使账户之前的登录信息无效。
     *
     * @param accountKey 指定的账户主键。
     */
    void invalid(StringIdKey accountKey) throws ServiceException;

    /**
     * 分页查询所有账户信息。
     *
     * @param pagingInfo 分页信息。
     */
    PagedData<Account> lookup(PagingInfo pagingInfo) throws ServiceException;


    /**
     * 分页查询所有账户信息。
     *
     * @param pagingInfo 分页信息。
     */
    PagedData<Account> lookup(String preset, Object[] objs, PagingInfo pagingInfo) throws ServiceException;

}
