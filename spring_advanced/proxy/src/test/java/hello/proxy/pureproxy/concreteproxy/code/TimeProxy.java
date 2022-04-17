package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

// 리스코프 치환 원칙을 만족하는거 같음
@Slf4j
public class TimeProxy extends ConcreteLogic {

    private final ConcreteLogic realLogic;

    public TimeProxy(ConcreteLogic realLogic) {
        this.realLogic = realLogic;
    }

    @Override
    public String operation() {

        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();
        String result = realLogic.operation();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeDecorator 종료 resultTime={}", resultTime);

        return result;
    }
}
