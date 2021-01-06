package com.sxh.demo.service.impl;

import com.sxh.demo.entity.User;
import com.sxh.demo.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SXH
 * @description UserServiceImpl
 * @date 2020/12/30 23:07
 */
@Service
public class UserServiceImpl implements UserService {
    // 创建map 集合存储数据
    private final Map<Integer, User> users = new HashMap<>();

    public UserServiceImpl() {
        this.users.put(1, new User("lucy", "woman", 20));
        this.users.put(2, new User("mick", "man", 30));
        this.users.put(3, new User("jack", "man", 50));
    }

    @Override
    public Mono<User> getUserById(int id) {
        return Mono.justOrEmpty(this.users.get(id));
    }

    @Override
    public Flux<User> getAllUser() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> saveUserInfo(Mono<User> userMono) {
        return userMono.doOnNext(person -> {
            // 向map 集合中放值
            users.put(users.size()+1, person);
        }).thenEmpty(Mono.empty());
    }
}
