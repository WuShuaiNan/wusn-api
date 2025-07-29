package com.wusn.wusn.api.service.video.impl;

import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.video.stack.bean.entity.Rtsp;
import com.wusn.video.stack.service.RtspMaintainService;
import com.wusn.wusn.api.service.video.RtspResponseService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class RtspResponseServiceImpl implements RtspResponseService {

    @DubboReference(group = "${dubbo.rbac.group}")
    private RtspMaintainService service;

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public Rtsp get(LongIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public LongIdKey insert(Rtsp rtsp) throws ServiceException {
        return service.insert(rtsp);
    }

    @Override
    public void update(Rtsp rtsp) throws ServiceException {
        service.update(rtsp);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public PagedData<Rtsp> lookup(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

}
