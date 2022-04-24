package hello.proxy.postpreprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {
    @Test
    void basicConfig() {
        // 아래가 스프링 컨테이너 만드는 것
        
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);

        //A는 빈으로 등록된다.
        A a = applicationContext.getBean("beanA", A.class);
        a.helloA();

        //B는 빈으로 등록되지 않는다. ( assertions junit꺼를 여기서는 씀.. Assertj껀 throws가 쫌 다름.. 이거 검색해서 알아보고 쓰라고함)
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class));
    }

    @Slf4j
    @Configuration
    static class BasicConfig {
        // 원래 @Bean 붙이면 bean은 method명으로 등록됨
        // 그래서 name을 beanA로 줘서 bean이름을 바꿈
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }
    }

    // 아래 A/B는 test용 bean을 만들기 위한 class
    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }
}
