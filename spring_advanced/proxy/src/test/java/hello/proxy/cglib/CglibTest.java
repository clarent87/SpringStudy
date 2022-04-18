package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        
        ConcreteService target = new ConcreteService(); // 타겟인 구체 클래스
        
        Enhancer enhancer = new Enhancer(); // cglib으로 만들 proxy의 factory인듯
        enhancer.setSuperclass(ConcreteService.class); // 구체 클래스 기반 프록시를 만들꺼니까 superclass에 세팅
        enhancer.setCallback(new TimeMethodInterceptor(target)); // TimeMethodInterceptor 만든거 callback으로 세팅
        ConcreteService proxy = (ConcreteService) enhancer.create(); // proxy를 만듬
        
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }

}
