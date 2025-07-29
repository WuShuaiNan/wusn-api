package com.wusn.wusn.api.config.beanTransform;

import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermission;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonPermissionGroup;
import com.dwarfeng.rbacds.sdk.bean.entity.FastJsonRole;
import com.dwarfeng.rbacds.sdk.bean.entity.JSFixedFastJsonPexp;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
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
public interface RbacMapper {

    // permission
    @Mappings({
            @Mapping(source = "key", target = "key")
    })
    List<FastJsonPermission> permission2JSFixedFastJsonPermission(List<Permission> list);
    PagedData<FastJsonPermission> permission2JSFixedFastJsonPermission(PagedData<Permission> pagedData);

    // permissionGroup
    @Mappings({
            @Mapping(source = "key", target = "key")
    })
    List<FastJsonPermissionGroup> permissionGroup2JSFixedFastJsonPermissionGroup(List<PermissionGroup> list);
    PagedData<FastJsonPermissionGroup> permissionGroup2JSFixedFastJsonPermissionGroup(PagedData<PermissionGroup> pagedData);

    // pexp
    @Mappings({
            @Mapping(source = "key", target = "key")
    })
    List<JSFixedFastJsonPexp> pexp2JSFixedFastJsonPexp(List<Pexp> list);
    PagedData<JSFixedFastJsonPexp> pexp2JSFixedFastJsonPexp(PagedData<Pexp> pagedData);

    // role
    @Mappings({
            @Mapping(source = "key", target = "key")
    })
    List<FastJsonRole> role2FastJsonRole(List<Role> list);
    PagedData<FastJsonRole> role2FastJsonRole(PagedData<Role> pagedData);
    
}
