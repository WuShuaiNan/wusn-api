package com.wusn.wusn.api.controller.video;

import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.JSFixedFastJsonPagedData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.wusn.video.sdk.bean.entity.JSFixedFastJsonRtsp;
import com.wusn.video.sdk.bean.entity.WebInputRtsp;
import com.wusn.video.stack.bean.entity.Rtsp;
import com.wusn.wusn.api.config.beanTransform.VideoMapper;
import com.wusn.wusn.api.service.video.RtspResponseService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller BOM树节点
 *
 * @author 吴帅男
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
@Slf4j
public class RtspController {

    @Autowired
    private RtspResponseService service;

    VideoMapper beanTransform = Mappers.getMapper(VideoMapper.class);

    @GetMapping("/rtsp/{id}/exists")
//    @LoginValidated
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

    @GetMapping("/rtsp/{id}/get")
//    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonRtsp> get(
            HttpServletRequest request,
            @PathVariable("id") long id
    ) {
        try {
            Rtsp rtsp = service.get(new LongIdKey(id));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonRtsp.of(rtsp)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/rtsp/insert")
//    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonLongIdKey> insert(
            HttpServletRequest request,
            @RequestBody WebInputRtsp rtsp
    ) {
        try {
            LongIdKey key = service.insert(WebInputRtsp.toStackBean(rtsp));
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonLongIdKey.of(key)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @PostMapping("/rtsp/update")
//    @LoginValidated
    public FastJsonResponseData<Object> update(
            HttpServletRequest request,
            @RequestBody WebInputRtsp rtsp
    ) {
        try {
            service.update(WebInputRtsp.toStackBean(rtsp));
            return FastJsonResponseData.of(ResponseDataUtil.good(null));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

    @GetMapping("/rtsp/{id}/delete")
//    @LoginValidated
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

    @GetMapping("/rtsp/lookup")
//    @LoginValidated
    public FastJsonResponseData<JSFixedFastJsonPagedData<JSFixedFastJsonRtsp>> lookup(
            HttpServletRequest request,
            @RequestParam("page") int page,
            @RequestParam("rows") int rows
    ) {
        try {
            PagedData<Rtsp> all = service.lookup(new PagingInfo(page, rows));
            PagedData<JSFixedFastJsonRtsp> transform = beanTransform.rtsp2JSFixedRtsp(all);
            return FastJsonResponseData.of(ResponseDataUtil.good(JSFixedFastJsonPagedData.of(transform)));
        } catch (ServiceException e) {
            return FastJsonResponseData.of(ResponseDataUtil.bad(e));
        }
    }

}
