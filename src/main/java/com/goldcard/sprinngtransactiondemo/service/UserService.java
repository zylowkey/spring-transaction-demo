package com.goldcard.sprinngtransactiondemo.service;

import com.goldcard.sprinngtransactiondemo.dao.UserRepository;
import com.goldcard.sprinngtransactiondemo.pojo.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//@Transactional 生效原则 1，除非特殊配置（比如使用 AspectJ 静态织入实现 AOP），否则只有定义在 public 方法上的 @Transactional 才能生效。
//@Transactional 生效原则 2，必须通过代理过的类从外部调用目标方法才能生效。
@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService self;

    public int createUserWrong1(String name) {
        try {
//            通过 this 自调用，没有机会走到 Spring 的代理类；
//            this 指针代表对象自己，Spring 不可能注入 this，所以通过 this 访问方法必然不是代理。
//            this.createUserPublic(new UserEntity(name));
            self.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //@Transactional  不能标记私有方法
    //Spring 默认通过动态代理的方式实现 AOP，对目标方法进行增强，private 方法无法代理到，Spring 自然也无法动态增强事务处理逻辑。
//    @Transactional
//    private void createUserPrivate(UserEntity entity) {
//        userRepository.save(entity);
//        if (entity.getName().contains("test"))
//            throw new RuntimeException("invalid username!");
//    }

    public int createUserWrong2(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }


    @Transactional
    public void createUserPublic(UserEntity entity) {
        //Spring 通过 AOP 技术对方法进行增强，要调用增强过的方法必然是调用代理后的对象。
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }
    //根据用户名查询用户数
    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}
