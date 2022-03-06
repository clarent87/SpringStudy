package hello.exception;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
// 서블릿 컨테이너가 하라는(제공하는) 대로 WebServerFactoryCustomizer<ConfigurableWebServerFactory> 를 이용함
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 404에러발생시 "/error-page/404" 컨트롤러를 호출해라! 란 코드
        // ErrorPageController로 들어가게된다.
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND,  "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        // RuntimeException 발생시 "/error-page/500"로 가라!
        // RuntimeException 예외의 자식 예외도 모두  "/error-page/500"로 보내줌
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        // 등록
        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
