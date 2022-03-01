package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {

    // 스프링 부트에서 필터 등록 방법
    // FilterRegistrationBean이거 제네릭인데 그냥 쓰나 보네
    @Bean
    public FilterRegistrationBean logFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(); // FilterRegistrationBean 객체 준비
        filterRegistrationBean.setFilter(new LogFilter()); // 만든 필터 등록
        filterRegistrationBean.setOrder(1); // 필터는 체이닝 되므로 순서가 있다. 순서 세팅
        filterRegistrationBean.addUrlPatterns("/*"); // 서블릿 필터는 request uri마다 적용할수 있다. 일단 모든 uri에 적용되게 셋팅.
                                                    // 그리고 한번에 여러패턴 지정도 가능하다고 함

        return filterRegistrationBean;
    }
}
