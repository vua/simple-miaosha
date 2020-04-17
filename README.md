# simple-miaosha
Spring Cloud 简易分布式秒杀项目

项目目前正在进行中...

| 功能 | 状态 |
| ------ | ------ |
| JWT单点登录,token机制 | ok || ------ | ------ |
| 社交登录,第三方授权登录 | half || ------ | ------ |
| 动态化URL,隐藏秒杀接口 | ok || ------ | ------ |
| 前端限流,冻结抢购button | ok || ------ | ------ |
| 后端限流,RateLimiter令牌桶 | no || ------ | ------ |
| 分布式锁redisson | ok || ------ | ------ |
| redis缓存 | ok || ------ | ------ |
| alipay支付接入,沙箱环境 | ok || ------ | ------ |
| 页面静态化 | no || ------ | ------ |
| 顺序消费 | ok || ------ | ------ |
| rocketmq重复消费问题 | no || ------ | ------ |
| 分布式事务,mq | ok || ------ | ------ |
| 全局异常处理 | no || ------ | ------ |
| admin后台管理,权限认证略 | ok || ------ | ------ |
| 异步编排提高吞吐量,CompleteableFuture | no || ------ | ------ |
| WebSocket向前端页面推送 | no || ------ | ------ |
| 想到了再加吧 | no || ------ | ------ |

## 模块关系
为了偷懒去除miaosha_admin模块,admin并入miaosha_web模块

![模块关系](https://github.com/vua/simple-miaosha/blob/master/module.png)
## 秒杀流程
![秒杀流程](https://github.com/vua/simple-miaosha/blob/master/process.png)
