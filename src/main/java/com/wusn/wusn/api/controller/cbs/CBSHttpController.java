package com.wusn.wusn.api.controller.cbs;

import com.dwarfeng.sfds.stack.service.LongIdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wusn.wusn.api.bean.entity.cbs.JacksonPaymentResponse;
import com.wusn.wusn.api.service.cbs.CBSHttpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller CBS HTTP接口
 *
 * @author wusn
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/cbs")
@CrossOrigin
@Slf4j
public class CBSHttpController {

    @Autowired
    private CBSHttpService cbsHttpService;

    @DubboReference
    private LongIdService longIdService;

    @Value("${cbs.url.payment}")
    private String paymentURL;

    @PostMapping("/payment-apply-common")
    public JacksonPaymentResponse paymentApplyCommon(
            @RequestBody Map<String, Object> map
    ) {
        try {
            log.info("请求体：{}", map);
            List<Map<String, String>> itemlist = (List<Map<String, String>>) map.get("list");
            String data = "";
            for (Map<String, String> item : itemlist) {
                data = "[\n" +
                        "    {\n" +
                        "        \"amount\": \"" + MapUtils.getString(item, "jhyk") + "\",\n" + // 金额，必填*
                        "        \"bankNum\": null,\n" + // 收方地区码，不填
                        "        \"busType\": \"202\",\n" + // 业务类型，202不能动
                        "        \"cnapsCode\": \"yxzh\",\n" + // 联行号，同行不填，跨行必填
                        "        \"currency\": \"10\",\n" +  // 币种。人民币传10，必填
                        "        \"payAccount\": \"" + MapUtils.getString(item, "fkyxzh") + "\",\n" + // 付款账号，必填*
                        "        \"personalFlag\": 0,\n" + // 公司标志，1-对私，0-对公，非必填
                        "        \"purpose\": \"12\",\n" + // 用途，200个字符，非必填
                        "        \"referenceNum\": \""+ longIdService.nextLongId() + "\",\n" + // 业务参考号，必填
                        "        \"revAccount\": \"" + MapUtils.getString(item, "ykdwbm") + "\",\n" + // 收款账号，必填*
                        "        \"revAccountName\": \"" + MapUtils.getString(item, "srmskdw") + "\",\n" +  // 收款账户名称，202-必填*
                        "        \"revBankName\": \"" + MapUtils.getString(item, "khyhsrm") + "\",\n" + // 收款银行名称，必填*
                        "        \"revBankType\": null,\n" + // 收款银行类型，非必填，通过银行名称或联行号带出
                        "        \"summary\": \"\",\n" + // 支付备注，长度200，非必填
                        "        \"urgentTag\": \"0\"\n" + // 加急标志，0-不加急，1-加急，2-特急，非必填
                        "    }\n" +
                        "]";
            }
            String response = cbsHttpService.post(paymentURL, data);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response, JacksonPaymentResponse.class);
        } catch (Exception e) {
            JacksonPaymentResponse jacksonPaymentResponse = new JacksonPaymentResponse();
            jacksonPaymentResponse.setCode("500");
            jacksonPaymentResponse.setMsg(e.getMessage());
            return jacksonPaymentResponse;
        }
    }

}
