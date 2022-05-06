package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        internal(); //내부 메서드 호출(this.internal())
                    // java에서 this.internal()로 호출해 준다. -> 바이트코드상 당연한 얘기
    }

    public void internal() {
        log.info("call internal");
    }
}
