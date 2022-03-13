package hello.typeconverter.formatter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Slf4j
public class MyNumberFormatter implements Formatter<Number> { // string formatter는 스프링이 기본으로 제공하니까 number를 예로 만듬

    @Override
    public Number parse(String text, Locale locale) throws ParseException {
        log.info("text={}, locale={}", text, locale);
        // "1,000" -> 1000
        // 근데 위 기능 직접 구현하면 골치아픔. 그래서 java에서 미리 해당 기능을 제공함 그게 NumberFormat
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        return format.parse(text); // 내부적으로 Number의 구현체인 Long이 반환되나
    }

    @Override
    public String print(Number object, Locale locale) {
        log.info("object={}, locale={}", object, locale);
        return NumberFormat.getNumberInstance(locale).format(object);
    }
}
