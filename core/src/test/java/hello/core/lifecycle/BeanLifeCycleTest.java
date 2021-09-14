package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close(); //스프링 컨테이너를 종료, ConfigurableApplicationContext 필요
                    // 왜냐면 ApplicationContext 에는 close method가 없다. 일반적으로 직접 닫는경우가 없어서.
                    // AnnotationConfigApplicationContext 는 ConfigurableApplicationContext를 상속하니까
                    // AnnotationConfigApplicationContext를 써도 된다.
    }

    @Configuration
    static class LifeCycleConfig{

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            // 출력 :
            // - 생성자 호출, url = null
            // - connect : null
            // - url = null message = 초기화 연결 메시지
            // 당연히 위와 같이 나올수 밖에 없음. 생성자 호출시점엔 url이 없음.
            // => 생성자에 초기화 작업  connect, call을 넣은 경우.

            networkClient.setUrl("http://hello-spring.dev"); // TODO: 자동 스캔으로는 이렇게 할수 있나?
            return networkClient; // 빈 등록
        }
    }
}
