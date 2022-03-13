package hello.typeconverter;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIntegerConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 포매터는 컨버터의 좀더 확장된 기능
    // 쨋든 여기에 컨버터 등록
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 아래 두개는 주석 처리. 우선순위 때문 ( 포매터 등록을 위해 주석 처리함 )
        // 우선순위는 컨버터가 포매터 보다 빠름.
        // 근데 String -> Integer, Integer-> String하는 컨버터, 포매터 두개가 있을땐 컨버터가 먼저 먹힘. 그래서 주석으로 뺌
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
        // 이렇게 하면 스프링 내부에서 쓰는 컨버전 서비스에 컨버터가 등록됨

        // 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
