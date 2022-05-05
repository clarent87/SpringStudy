package hello.aop.pointcut;


import hello.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired
    Child child; // 빨간줄은 잘못 처진것일듯, 영한님 화면도 빨간줄 처짐

    @Test
    void success() {
        log.info("child Proxy={}", child.getClass());
        child.childMethod(); //부모, 자식 모두 있는 메서드
        child.parentMethod(); //부모 클래스만 있는 메서드
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {
        } //부모에만 있는 메서드
    }

    // ClassAop 어노테이션은 Child에만 붙임
    @ClassAop
    static class Child extends Parent {
        public void childMethod() {
        }
    }

    // ArgTest나 WithinTest의 경우는 그냥 포인트컷객체 만들어서 match함수로 테스트를 했었는데
    // @Target이나 @Within의 경우 실제 객체를 기반으로 판단하기 때문에, 위와 같은 테스트는 불가능
    // 따라서 Bean 객체를 만들어서 테스트를 진행하는 수고를 함

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect {

        // @target: 인스턴스 기준으로 모든 메서드의 조인 포인트를 선정, 부모 타입의 메서드도 적용
        // execution(* hello.aop..*(..)) 는 hello.aop 패키지 밑에만 적용하겠다는것
        // @target(hello.aop.member.annotation.ClassAop) 는 ClassAop 어노테이션이 붙은거를 타겟으로 한단것
        @Around("execution(* hello.aop..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //@within: 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정, 부모 타입의 메서드는 적용되지 않음
        @Around("execution(* hello.aop..*(..)) && @within(hello.aop.member.annotation.ClassAop) ")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}