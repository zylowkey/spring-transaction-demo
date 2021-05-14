package com.goldcard.sprinngtransactiondemo.Controller;

import com.goldcard.sprinngtransactiondemo.pojo.UserEntity;
import com.goldcard.sprinngtransactiondemo.service.UserService;
import com.goldcard.sprinngtransactiondemo.service.UserService1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/test1")
@Slf4j
public class UserController1 {
    @Autowired
    private UserService1 userService1;

    @PostMapping("wrong1")
    public void wrong1(@RequestParam("name") String name) {
        userService1.createUserWrong1(name);
    }


    @PostMapping("wrong2")
    public void wrong2(@RequestParam("name") String name) throws IOException {
        userService1.createUserWrong2(name);
    }
}
