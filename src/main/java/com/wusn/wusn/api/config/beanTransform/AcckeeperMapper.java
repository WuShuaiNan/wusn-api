package com.wusn.wusn.api.config.beanTransform;

import com.dwarfeng.acckeeper.sdk.bean.entity.JSFixedFastJsonAccount;
import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper bean转换设置。
 *
 * @author wusn
 * @since 1.0.0.a
 */
@Mapper
public interface AcckeeperMapper {

    // account
    @Mappings({
            @Mapping(source = "key", target = "key")
    })
    List<JSFixedFastJsonAccount> account2JSFixedFastJsonAccount(List<Account> list);
    PagedData<JSFixedFastJsonAccount> account2JSFixedFastJsonAccount(PagedData<Account> pagedData);
    
}
