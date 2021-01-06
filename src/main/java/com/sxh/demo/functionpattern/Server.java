package com.sxh.demo.functionpattern;

import com.sxh.demo.functionpattern.handler.UserHandler;
import com.sxh.demo.service.UserService;
import com.sxh.demo.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

/**
 * @author SXH
 * @description 函数式编程处理请求
 * @date 2021/1/6 21:30
 */
public class Server {
    public static void main(String[] args) throws IOException {
        // 执行main 方法，取日志中的端口，http://localhost:4182/function/user/1  访问
        // [reactor-http-nio-1] DEBUG reactor.netty.tcp.TcpServer - [id: 0x0d758110, L:/0:0:0:0:0:0:0:0:4182] Bound new server
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    /**
     * 1.创建Router 路由
     */
    public RouterFunction<ServerResponse> routingFunction() {
        // 创建service 实例
        UserService userService = new UserServiceImpl();
        // 创建handler 实例
        UserHandler handler = new UserHandler(userService);
        // 路由处理
        return RouterFunctions.route(
                GET("/function/user/{id}").and(accept(MediaType.APPLICATION_JSON)),
                handler::getUserById)
        .andRoute(GET("/function/userAll").and(accept(MediaType.APPLICATION_JSON)),
                handler::getAllUser);
    }

    /**
     * 2.创建服务器完成适配
     */
    public void createReactorServer() {
        // 路由和handler 适配
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

        // 创建服务器
        HttpServer httpServer = HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }

}
