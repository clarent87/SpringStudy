package hello.proxy.config.v6_aop.aspect;


import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }
    // [*] 아래 Around 붙은 method는 여기에 여러개 둘수 있다. 
    // [*] 그러면 각각이 advisor로 생성되어 @Aspect 어드바이저 빌더에 등록됨
    
    // 3. 1,2가 합쳐지니.. advisor가 된다.
    @Around("execution(* hello.proxy.app..*(..))") // 1. 이게 포인트컷.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // 2. 아래 method body에 advice 로직이 들어감
        TraceStatus status = null;

        // [*] 아래 처럼 joinPoint에서 다양한 정보 얻을수 있다.
        // log.info("target={}", joinPoint.getTarget()); //실제 호출 대상
        // log.info("getArgs={}", joinPoint.getArgs()); //전달인자
        // log.info("getSignature={}", joinPoint.getSignature()); //join point 시그니처

        try {
            String message = joinPoint.getSignature().toShortString(); // 요거 LogTraceAdvice랑 비교해보면 좋다.
            status = logTrace.begin(message);

            //로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
