package com.sxh.demo.annotationpattern.controller;

import com.sxh.demo.entity.User;
import com.sxh.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author SXH
 * @description UserController
 * @date 2020/12/31 0:25
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Mono<User> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/userAll")
    public Flux<User> getUserAll() {
        return userService.getAllUser();
    }

    @PostMapping("/saveUser")
    public void addUser(@RequestBody User user) {
        userService.saveUserInfo(Mono.just(user));
    }
}
