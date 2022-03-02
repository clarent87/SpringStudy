package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer { // WebMvcConfigurer는 인터셉터 등록 때문에 impl하는거


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**") // 패턴이 필터의 패턴이랑은 전혀 다르다. pdf에 정리함 ( 일단 모든 uri에 인터셉터 적용 )
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 여기에는 인터셉터 적용이 안됨 ( 옆 uri접근은 로그를 남기지 않는다. )

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");
    }

    // 스프링 부트에서 필터 등록 방법
    // FilterRegistrationBean이거 제네릭인데 그냥 쓰나 보네
    // 로그 인터셉터 개발후 bean은 주석 처리함
//    @Bean
    public FilterRegistrationBean logFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(); // FilterRegistrationBean 객체 준비
        filterRegistrationBean.setFilter(new LogFilter()); // 만든 필터 등록
        filterRegistrationBean.setOrder(1); // 필터는 체이닝 되므로 순서가 있다. 순서 세팅
        filterRegistrationBean.addUrlPatterns("/*"); // 서블릿 필터는 request uri마다 적용할수 있다. 일단 모든 uri에 적용되게 셋팅.
                                                    // 그리고 한번에 여러패턴 지정도 가능하다고 함

        return filterRegistrationBean;
    }

//    @Bean
    public FilterRegistrationBean loginCheckFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(); // FilterRegistrationBean 객체 준비
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 만든 필터 등록
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
                // addUrlPatterns에 로그인 필터 적용할 list를 나열하는 방식으로 로그인 필터 구현가능
                // 근데 이러면 page 추가시 마다 코드에 추가해야함.. 그래서 whitelist방식으로 구현하는것이 편하다고 함


        return filterRegistrationBean;
    }
}
