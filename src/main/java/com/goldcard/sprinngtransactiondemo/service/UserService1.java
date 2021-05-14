package com.goldcard.sprinngtransactiondemo.service;

import com.goldcard.sprinngtransactiondemo.dao.UserRepository;
import com.goldcard.sprinngtransactiondemo.pojo.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
//事务即便生效也不一定回滚
//通过AOP实现事务可以理解为，使用try ... catch...来包裹标记了 @Transactional注解的方法，当方法出现了异常并且满足一定条件时，在catch里面可以设置事务回滚，没有异常则直接提交事务
//一定条件：
// 1、只有异常传播出了标记了 @Transactional 注解的方法，事务才能回滚
// 2、默认情况下，出现 RuntimeException（非受检异常）或 Error 的时候，Spring 才会回滚事务。
@Service("userService1")
@Slf4j
public class UserService1 {
    @Autowired
    private UserRepository userRepository;

    //异常无法传播出方法，导致事务无法回滚
    @Transactional
    public void createUserWrong1(String name) {
        try {
            userRepository.save(new UserEntity(name));
            throw new RuntimeException("error");
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
            //可以手动设置让当前事务处于回滚状态：
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    //即使出了受检异常也无法让事务回滚
    //在注解中声明，期望遇到所有的 Exception 都回滚事务（来突破默认不回滚受检异常的限制）：
    @Transactional(rollbackOn = Exception.class)
    public void createUserWrong2(String name) throws IOException{
        userRepository.save(new UserEntity(name));
        otherTask();
    }

    //因为文件不存在，一定会抛出一个IOException
    private void otherTask() throws IOException {
        Files.readAllLines(Paths.get("file-that-not-exist"));
    }
}
