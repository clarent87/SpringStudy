package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    // 빈후처리기 에서 한번 나옴 -> AspectJExpressionPointcut
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        // 테스트 실행전 MemberServiceImpl.class에서 hello method 뽑음
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class); // hello method의 param이 string임
    }

    @Test
    void printMethod() {
        // [*] 그냥 method 정보 찍으면 아래와 같이 나옴
        // [*] execution PCD에 주었던 aspectj 문법이 아래와 매칭되는 거라서 찍어봄
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }
}
