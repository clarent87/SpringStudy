package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

    // 간단한 소켓 연결 프로그램이라고 가정.

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("url = " + url + " message = " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 아래가 초기화 작업
        connect();
        call("초기화 연결 메시지"); // 이게 초기화 작업
    }

    @Override
    public void destroy() throws Exception {
        disconnect();
    }
}
