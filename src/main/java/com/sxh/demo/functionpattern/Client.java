package com.sxh.demo.functionpattern;

import com.sxh.demo.entity.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.sql.SQLOutput;

/**
 * @author SXH
 * @description 除了网页工具调用函数式编程写的服务外，还可以通过WebClient 编程调用
 * @date 2021/1/6 22:01
 */
public class Client {
    public static void main(String[] args) {
        // 定义client 调用的服务地址
        // 注：端口号根据每次server 启动有所变化
        WebClient webClient = WebClient.create("http://localhost:4182");
        // 编写调用方法
        // 根据id 查询
        String id = "1";
        User userResult = webClient.get()
                .uri("/function/user/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class)
                .block();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userResult);

        // 查询所有
        Flux<User> userFlux = webClient.get()
                .uri("/function/userAll")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class);
//        userFlux.map(user -> user.getName()).buffer().doOnNext(System.out::println).blockFirst();
        userFlux.buffer().doOnNext(System.out::println).blockFirst();
    }
}
