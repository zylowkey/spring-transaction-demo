# spring-transaction-demo

业务代码中最常见的使用数据库事务的方式，即 Spring 声明式事务，可能遇到的三类坑，包括：

第一，因为配置不正确，导致方法上的事务没生效。我们务必确认调用 @Transactional 注解标记的方法是 public 的，并且是通过 Spring 注入的 Bean 进行调用的。
第二，因为异常处理不正确，导致事务虽然生效但出现异常时没回滚。Spring 默认只会对标记 @Transactional 注解的方法出现了 RuntimeException 和 Error 的时候回滚，如果我们的方法捕获了异常，那么需要通过手动编码处理事务回滚。如果希望 Spring 针对其他异常也可以回滚，那么可以相应配置 @Transactional 注解的 rollbackFor 和 noRollbackFor 属性来覆盖其默认设置。
第三，如果方法涉及多次数据库操作，并希望将它们作为独立的事务进行提交或回滚，那么我们需要考虑进一步细化配置事务传播方式，也就是 @Transactional 注解的 Propagation 属性。
