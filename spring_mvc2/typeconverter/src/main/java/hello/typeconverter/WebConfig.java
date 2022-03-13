package hello.typeconverter;

import hello.typeconverter.converter.IntegerToStringConverter;
import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIntegerConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 포매터는 컨버터의 좀더 확장된 기능
    // 쨋든 여기에 컨버터 등록
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addConverter(new IntegerToStringConverter());
        // 이렇게 하면 스프링 내부에서 쓰는 컨버전 서비스에 컨버터가 등록됨
    }
}
