package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    // [*] 포인트컷 매개변수 전달 이용
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable { // Retry 가 어노테이션인거 잘 봐둬야함 (type이 어노테이션..)

        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value(); // [*] 어노테이션에 있는 밸류 가져옴

        Exception exceptionHolder = null;

        // retry로직은 doWhile로 만들수도 있고. 다양하다고 함
        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}

