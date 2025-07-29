package com.wusn.wusn.api.service.video;

import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.video.stack.bean.entity.Rtsp;

/**
 * Response rtsp响应服务。
 *
 * @author wusn
 * @since 1.0.0.a
 */
public interface RtspResponseService {

    boolean exists(LongIdKey key) throws ServiceException;

    Rtsp get(LongIdKey key) throws ServiceException;

    LongIdKey insert(Rtsp rtsp) throws ServiceException;

    void update(Rtsp rtsp) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<Rtsp> lookup(PagingInfo pagingInfo) throws ServiceException;

}
