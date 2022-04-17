package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 프록시 class를 만드는 것. AInterface, BInterface를 대상으로 하는...
 * 이 클래스는 모든 객체에 대해 동적으로 method를 호출할수 있게 하는 class + 시간측정.
 * 앞선 pureproxy에서는 특정 interface를 대상으로 할수 밖에 없었기에, 각강의 interface마다 proxy를 만들어야 했음
 */
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target; // 프록시의 타겟. 아무 타겟이나 전부 받기 위해 Object로 진행

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    // Object proxy : 프록시 자신
    // method : 이값도 알아서 넘어 온다고 함
    // 약간 c++에서도 param1은 this가 넘어왔었는데.. assem에서.. 약간 그런 느낌인가?
    // python의 instance method도 마찬가지로 자기 자신인 self가 넘어 왔었음..
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // method 구분은 method.getName().startsWith("get") 처럼 해야함. 이걸 바탕으로 if/else

        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = method.invoke(target, args); // 타겟 객체에 대해 동적으로 메소드 호출

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);

        return result;
    }
}
