# wusn-api
- `达娃里氏`个人接口集成服务。

## 1. 项目简介
- 本项目主要用于处理前后端数据中转和第三方接口集成服务。
- 目前已实现或集成如下接口：
  - Hello World 连通性测试接口。
  - 济南二机床泛微 OA C04物资采购资金使用审批流程与招行 CBS 支付系统的集成。
  - 第三方泛微 OA 通知接口。
  - Rtsp 监控视频流转 Flv 视频流接口。
- 目前已实现如下工具：
  - 基于 FFmpeg 的视频流转换工具类。
  - 基于 bouncycastle 的国密 SM2 工具类。

## 2. 项目使用说明
- 待补充~

## 3. TODO
### 3.1. 项目开发
- 新增 tomcat 外置配置读取机制。

### 3.2. 接口开发
- 新增 MDM 主数据人员信息主动同步接口，将人员信息推送给[@DwArFeng](https://github.com/DwArFeng) 的[account-keeper](https://github.com/DwArFeng/account-keeper)。
- 新增 MDM 主数据部门信息主动同步接口。
- 新增 Rtsp 监控视频流转 WebRTC 视频流接口。
- 新增 泛微 OA 原生通知接口。
- 新增 Oauth 2.0 接口代理，并与 [@DwArFeng](https://github.com/DwArFeng) 的[account-keeper](https://github.com/DwArFeng/account-keeper)进行集成。

## 4. 关于项目
- 如果你觉得这个项目不错，请给个star，谢谢！
- 本项目使用 GPL 3.0 开源协议，请遵守协议。

## 5. 作者联系方式：
1. **邮箱：** wu_shuainan@qq.com
2. **微信：** wu_shuainan

## 6. 特别鸣谢
- 感谢 [@DwArFeng](https://github.com/DwArFeng) 提供的 [Subgrade](https://github.com/DwArFeng/subgrade) 通用工具库。
