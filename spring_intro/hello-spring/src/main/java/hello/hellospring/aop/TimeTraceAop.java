package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") //  hello.hellospring 하위에 다적용 하라는 말. ( 경로는 ref보고 작성하라함. )
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toString()); // 어떤 함수를 호출에서 AOP가 동작했는지 확인 가능.
              // 위 내용은 함수 호출 들어갈때 찍고
        try{
            return joinPoint.proceed(); // 이게 함수 호출 같음.. 거의 의미상 맞네.. pdf에 나옴

        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
            // 이건 함수 나올때 찍고..
        }

    }
}
