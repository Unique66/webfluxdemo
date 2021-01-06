package com.sxh.demo.service;

import com.sxh.demo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author SXH
 * @description UserService
 * @date 2020/12/30 23:04
 */
public interface UserService {
    // 根据id 查询用户
    Mono<User> getUserById(int id);
    // 查询所有用户
    Flux<User> getAllUser();
    // 添加用户
    Mono<Void> saveUserInfo(Mono<User> user);
}
