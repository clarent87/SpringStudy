package hello.login.web.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig); // 이거 필요 없나봄 -> default 메소드 호출인데. body가 없음. 그러니 빼도 됨

        log.info("log filter init");
    }

    // 모든 사용자의 uri를 남김
    // 원래 서블릿이 설계될때, http 요청말고 다른것들도 받을수 있게 설계가 되었다고함, 그래서 ServletRequest가 사용됨
    // 근데 대부분 httpServletRequest만 쓴다. 그래서 아래 처럼 다운캐스팅 해서
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        // ServletRequest는 기능이 없으니 httpServletRequest로 다운캐스팅 한다.

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString(); // 그냥 request 구분용

        try {
            log.info("REQUEST [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 이거 중용함. 이거 호출안하면 다음 필터가 호출되지 않음 (필터 체인 )
            // 이후 필터 체인 -> 컨트롤러 처리후.
            // control이 여기로 떨어진다고 함 ( 로그 찍힌거 보면 그렇게 이해할 수 밖에 없긴 하네..)
        } catch (Exception e) {
            throw e;
        } finally {
            // HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러 -> 필터 ( 이위치에서 아래가 찍힘 )
            // 실제로 컨트롤러 error로그와 여기 로그찍히는 순서가 아래와 같음 ( 그래서 위 내용이 맞는거 같음 )
            // REQUEST -> error -> RESPONSE
            log.info("RESPONSE [{}][{}]", uuid, response);
        }
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
        log.info("log filter destroy");
    }
}
