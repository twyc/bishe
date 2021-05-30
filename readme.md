# 旅游景点推荐系统
毕设 

使用了apriori算法 写在RuleService里

springboot+vue

html文件夹中使用的是单个文件集成vue2进行跳转

目前维护的是web文件夹下使用vue3脚手架搭建的spa 交互和之前一样

##TODO：
### p0
1. 有一些写在controller层的逻辑 预期下沉到service层
2. 前端登录状态改造
2. 用拦截器和AOP实现登录校验
3. 之前的原始数据采用手动访问接口的方式访问数据库 考虑搭建管理员系统 //答辩被怼的点
4. 爬取游记的Python文件改造 集成至这个系统 利用springboot的定时任务定时爬取

### p1
1. 代码规范改造
2. 实践DDD 将service层的逻辑按照语义迁移至domainModel 进行对象的交互