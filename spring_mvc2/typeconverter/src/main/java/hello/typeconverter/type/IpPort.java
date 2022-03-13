package hello.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;


// "127.7.7.1:8080" 문자가 오면 아래 타입으로 변환해 주기 위함. 또는 반대로 사용하기 위해 만듬
// TODO: 근데 아규먼트 리졸버 login에서 만든거랑 차이가 있나? 컨버터랑?
@Getter
@EqualsAndHashCode // ip, port가 같으면 equals에서 true 나온다고 함
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}