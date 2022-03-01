package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    // default method 지원하기 때문에 모든 abstract method를 구현할 필요는 없다.


    // 화이트리스트 준비. 로그인 없어도 아래 page는 접근 가능해야함. /css는 당연히 받아야 하고.
    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("미인증 사용자 요청 {}", requestURI);

                    // 로그인으로 redirect
                    // 이거 신기하네.. client redirect 시킬려면 sendRedirect이용해도 된다.
                    // redirectURL 이부분도 신기.
                    // 이를테면 loginpage, A page, B page가 있을때 사용자가 B page에서 권한이 없어서
                    // login 페이지로 갔을경우. 로그인 성공시 B page로 오게하는 방법이라고 함. -> 자동은 아님
                    // 해당 request param은 컨트롤러 loginv4에서 처리해줘야함.
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return; // 여기가 중요! 미인증 사용자는 다음으로 진행하지 않고 여기서 끝 ( 즉 chain.doFilter 안탐 )
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함
                    // 여기서 예외를 처리해버리면. 정상동작이 되므로...
        } finally {
            log.info("인증 체크 필터 종료 {} ", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        // 스프링에서 제공하는 패턴 매칭 class..
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
