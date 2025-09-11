package com.wusn.wusn.api.controller.iot;

import com.dwarfeng.subgrade.sdk.bean.dto.FastJsonResponseData;
import com.dwarfeng.subgrade.sdk.bean.dto.ResponseDataUtil;
import com.wusn.wusn.api.service.iot.RSAResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller RSA
 *
 * @author 吴帅男
 * @since 1.0.0.a
 */
@RestController
@RequestMapping("/iot")
@CrossOrigin
@Slf4j
public class RSAController {

    @Autowired
    private RSAResponseService rsaResponseService;

    @GetMapping("/rsa/encrypt")
    public FastJsonResponseData<String> encrypt(
            HttpServletRequest request,
            @RequestParam("publicKey") String publicKey,
            @RequestParam("data") String data
    ) {
        String encryptedData = rsaResponseService.encrypt(publicKey, data);
        return FastJsonResponseData.of(ResponseDataUtil.good(encryptedData));
    }

}
