package hello.proxy.config;

import hello.proxy.app.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppV1Config {
    @Bean
    public OrderControllerV1 orderControllerV1() {
        // Bean 등록할때 RequestMapping 또는 @Controller가 있으면 Controller로 인식 하나봄
        // 근데 @Controller 붙이면 자동 등록되긴 할듯
        return new OrderControllerV1Impl(orderServiceV1());
    }

    @Bean
    public OrderServiceV1 orderServiceV1() {
        return new OrderServiceV1Impl(orderRepositoryV1());
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        return new OrderRepositoryV1Impl();
    }
}
