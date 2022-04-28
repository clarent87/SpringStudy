package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    //hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* hello.aop.order..*(..))") //pointcut expression
    private void allOrder() {} //pointcut signature -> allOrder method에 파라메터가 없는데, 파라메터 넣을수도 있다함
                                // 이렇게 하면 포인트 컷 재사용 가능. 그리고 method명으로 의미 부여가능

    @Around("allOrder()") // [*] 이게 위 포인트컷 사용 방식 pointcut signature에 파라메터가 있었다면, 그것까지 맞춰야한다.
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

}
