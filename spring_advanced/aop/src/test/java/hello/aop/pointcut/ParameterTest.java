package hello.aop.pointcut;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }


    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.member..*.*(..))")
        private void allMember() {
        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            // [*] 역시 생각한 대로 joinPoint를 이용해서 param값 가져올수 있네
            // [*] 근데 이렇게 뽑는거는 가독성이 좋지가 않다.
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg,..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            // [*] joinPoint에서 파라메터 꺼내는 것보다 훨씬 깔끔
            // [*] args에 arg란 파라메터 명을 method에서도 맞춰 줘야한다. Object arg라고 그래서 파라메터 이름을 쓴것
            // [*] args의 arg는 method에 명시한 parameter type을 따라간다. 즉 arg는 Object 타입
            log.info("[logArgs2]{}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Before("allMember() && args(arg,..)") // args(String,..) 임.
        public void logArgs3(String arg) {
            // [*] 위 꺼보다 훨씬더 깔끔함.
            log.info("[logArgs3] arg={}", arg);
        }

        // 아래에서는 this, target을 일부 소개

        @Before("allMember() && this(obj)") // this(MemberService) 임
        public void thisArgs(JoinPoint joinPoint, MemberService obj) { // JoinPoint는 당연히 없어도 됨. before 어드바이스 소개시 나옴
            log.info("[this]{}, obj={}", joinPoint.getSignature(), obj.getClass());
        }

        // [*] this와 target은 비슷하지만 차이가 있음
        // [*] this는 컨테이너에 있는 bean을 가져옴, 즉 proxy 객체
        // [*] target은 실제 target 객체를 가져옴
        @Before("allMember() && target(obj)") // target(MemberService) 임
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target]{}, obj={}", joinPoint.getSignature(), obj.getClass()); // obj=class hello.aop.member.MemberServiceImpl
        }

        // 아래처럼 annotation 정보를 받아올수도 있다
        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotation);
        }

        // annotation에 넣어두었던 value도 가져올수 있음
        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }

    }
}
