package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject {

    private final Subject target; // proxy 입장에서 실제 객체는 target이라고 부름
    private String cacheValue;


    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {

        log.info("프록시 호출");

        if (cacheValue == null) {
            cacheValue = target.operation();
        }

        return cacheValue;
    }
}
