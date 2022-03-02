package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    
    public static final String LOG_ID = "logId";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString(); // uuid를 afterCompletion에 찍어주고 싶다고 이걸 class field로 만들면 안됨
                                                    // 왜냐면 LogInterceptor가 싱글톤이기 때문

        request.setAttribute(LOG_ID, uuid); // uuid를 afterCompletion에서도 쓰기위해 setAttribute로 넣음

        //@RequestMapping을 쓰면 선택되는 핸들러는 -> HandlerMethod ( 이게 넘어온다함 )
        //정적 리소스사용시 선택되는 핸들러는 -> ResourceHttpRequestHandler ( 이게 넘어온다함 )
        // HandlerMethod,ResourceHttpRequestHandler 는 모두 핸들러가 맞음
        // 내가 오해하고 있던게 @Controller가 컨트롤러가 아님.
        // mvc1의 초반에 class당 하나의 uri를 처리하던 그거,즉 특정 uri를 처리하는 녀석이 컨트롤러 == 핸들러임.
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;//호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }

        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler); // handelr는 컨트롤러다. 선택된 컨트롤러를 찍어줄수 있음
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView); // 모델앤뷰 내용 출력
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID); // uuid의 저장시 키를 logId로 함

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        if (ex != null) {
            log.error("afterCompletion error!!", ex); // 오류 로그 찍을떈 `{}` 이거 안해도 된다. error 메소드에 throw를 받을수 있는 param이 있음
        }

    }
}
