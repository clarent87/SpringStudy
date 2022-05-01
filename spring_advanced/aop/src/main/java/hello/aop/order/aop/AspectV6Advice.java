package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[around][트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            //@AfterReturning
            log.info("[around][트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            //@AfterThrowing
            log.info("[around][트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            //@After
            log.info("[around][리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    // JoinPoint는 aop 적용 위치. 이거 aop 개념에 나옴
    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        // joinPoint 필요없으면 파라메터 없이 method 작성해도 된다.
        log.info("[before] {}", joinPoint.getSignature());
        // 이후에 joinpoint는 자동으로 실행된다. (즉, target이 호출된다는것)
    }

    // returning = "result" 이므로 method의 result 변수에 target의 반환값이 담겨서 온다.
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        // result값 조작은 불가능.
        // 무슨말이냐면 target의 리턴값을 딴걸로 치환해서 반환할수는 없다는 말
        // 단. result를 조작할수는 있음. ( ex. result 객체 안에 변수가 있고 setter가 지원되면 해당 필드를 변경할수는 있음)

        // 현재 아래 advice의 target은 public void orderItem(String itemId) 이다
        // returning 절에 지정된 타입의 값은 String인데 target method는 void return이므로, 어드바이스 적용 대상에서 제외됨
        // 이경우 적용하고 싶다면 Object result로 해주면 된다.
        // [*] intellij의 경우 advice옆에 icon으로 해당 어드바이스가 적용되는 method를 보여주는데, target이 void인경우는, 잘 인식하지 못하는듯
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
        // throw e는 자동으로 호출됨 @Before 호출 후 joinPoint.proceed(); 호출되는 것 처럼
    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
