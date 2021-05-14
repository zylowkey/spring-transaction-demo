package com.goldcard.sprinngtransactiondemo.Controller;

import com.goldcard.sprinngtransactiondemo.pojo.UserEntity;
import com.goldcard.sprinngtransactiondemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("wrong1")
    public int wrong1(@RequestParam("name") String name) {
//        return userService.createUserWrong1(name);
        return userService.createUserWrong2(name);
    }

    @PostMapping("right2")
    public int right2(@RequestParam("name") String name) {
        try {
            userService.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userService.getUserCount(name);
    }
}
