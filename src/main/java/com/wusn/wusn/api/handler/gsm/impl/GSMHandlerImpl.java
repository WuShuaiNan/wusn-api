package com.wusn.wusn.api.handler.gsm.impl;

import com.fazecast.jSerialComm.SerialPort;
import com.wusn.wusn.api.bean.entity.gsm.JacksonGSMResponse;
import com.wusn.wusn.api.bean.entity.gsm.JacksonSmsInfo;
import com.wusn.wusn.api.handler.gsm.GSMHandler;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.Date;

@DubboService
@Component
public class GSMHandlerImpl implements GSMHandler {

    @Override
    public JacksonGSMResponse sentMessage(JacksonSmsInfo smsInfo) {
        JacksonGSMResponse response = new JacksonGSMResponse();
        response.setPhoneNum(smsInfo.getPhoneNum());
        response.setMessage(smsInfo.getMessage());
        response.setSendTime(new Date());
        try {
            // 测试时我采用的是VSPD虚拟串口软件，生成了一对串口COM1和COM2，此处实现的是实例化COM1端口，并操作COM1向COM2发送字符串
            // 串口监视我采用的是Arduino自带的串口监视器
            // 1.获取串口列表，根据串口列表找到需要的串口
            // TODO 获取SIM900A、SIM800C的串口信息
            SerialPort comPort = SerialPort.getCommPorts()[0];

            // 2.打开串口（打开后需要延时0.1秒再操作，否则会操作端口失败）
            if (!comPort.isOpen()) {
                comPort.openPort();
                Thread.sleep(100);
            }

            // 3.设置串口超时时间（不设置也可以，不影响通信）
            comPort.setComPortTimeouts(
                    SerialPort.TIMEOUT_READ_SEMI_BLOCKING,
                    1000,
                    1000);

            // 4.设置串口通信参数（波特率，数据位，停止位，校验位，是否开启RS485串口模式），RS485串口模式默认为不开启
            // TODO 参数需要更换为SIM900A和SIM800C默认的
            comPort.setComPortParameters(
                    9600,
                    8,
                    SerialPort.ONE_STOP_BIT,
                    SerialPort.NO_PARITY
            );

            // 5.获取需要传输的字节数组
            // TODO 需要换成包含手机号和短信内容的AT指令
            byte[] bytes = "Hello World".getBytes();

            // 6.向串口发送数据
            comPort.writeBytes(bytes, bytes.length);
            Thread.sleep(100);

            // TODO 7.获取返回结果，并记录相关日志

            response.setSuccessFlag(true);
            response.setError("");
            return response;
        } catch (Exception e) {
            response.setSuccessFlag(false);
            response.setError(e.toString());
            return response;
        }
    }
}
