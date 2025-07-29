package com.wusn.wusn.api.config.beanTransform;

import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.wusn.video.sdk.bean.entity.JSFixedFastJsonRtsp;
import com.wusn.video.stack.bean.entity.Rtsp;
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
public interface VideoMapper {

    // rtsp
    @Mappings({
            @Mapping(source = "key", target = "key")
    })
    List<JSFixedFastJsonRtsp> rtsp2JSFixedRtsp(List<Rtsp> list);
    PagedData<JSFixedFastJsonRtsp> rtsp2JSFixedRtsp(PagedData<Rtsp> pagedData);

}
