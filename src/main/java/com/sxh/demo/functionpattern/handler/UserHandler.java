package com.sxh.demo.functionpattern.handler;

import com.sxh.demo.entity.User;
import com.sxh.demo.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author SXH
 * @description UserHandler  HandlerFunction 具体操作
 * @date 2021/1/6 19:59
 */
public class UserHandler {
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    // 根据id 查询
    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        // 获取请求参数
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));
        // 调用service 方法获取结果
        Mono<User> userMono = userService.getUserById(id);

        // 空值处理
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        // 由于是响应式编程，需要将Mono<User> 转为流
        // 可以使用Reactor 的flatMap 来转换
        Mono<ServerResponse> result = userMono.flatMap(person -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromObject(person)))
                .switchIfEmpty(notFound);
        return result;
    }
    // 查询所有
    public Mono<ServerResponse> getAllUser(ServerRequest serverRequest) {
        // 调用service 拿查询结果
        Flux<User> allUser = userService.getAllUser();
        // 将结果放到ServerResponse 返回
        Mono<ServerResponse> result = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(allUser, User.class);
        return result;
    }
    // 添加操作
    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        // 获得参数
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        // 调用service 方法
        Mono<Void> voidMono = userService.saveUserInfo(userMono);
        // 返回结果
        Mono<ServerResponse> result = ServerResponse.ok().build(voidMono);
        return result;
    }
}
