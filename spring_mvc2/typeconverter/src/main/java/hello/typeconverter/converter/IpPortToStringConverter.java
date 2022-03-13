package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort, String> {


    @Override
    public String convert(IpPort source) {
        log.info("convert source={}", source);
        // IpPort객체 -> "127.0.0.1:8080"
        return source.getIp() + ":" + source.getPort(); // 원래 문자열 + 연산에 int 넣으면 알아서 변환 되나봄
    }
}
