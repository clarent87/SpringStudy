package hello.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component {

    // 아래 contructor와 변수는 MessageDecorator에도 있음.
    // 즉 중복. 이건 abstract class에 올릴수 있다고 함 -> 뒤에 나올예정인가봄
    private final Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {

        log.info("TimeDecorator 실행");

        long startTime = System.currentTimeMillis();
        String result = component.operation();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeDecorator 종료 resultTime={}ms", resultTime);
        return result;
    }

}
