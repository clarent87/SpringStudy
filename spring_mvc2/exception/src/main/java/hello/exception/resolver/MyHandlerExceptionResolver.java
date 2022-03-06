package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        log.info("call resolver", ex); // excepetion은 로그 찍을떄 {} 이용할 필요 없이 param으로 던지면 된다.

        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // ex로 넘어온 error을 치환함.
                return new ModelAndView(); //  return이 예외가 없었던 거처럼 정상적으로 처리됨
                                            // 근데 was까지 가면 was에서 sendError를 보고 400에러 처리해줌
                                            // 그리고 view 값이 없어서 일단 view resolver는 동작하지 않나봄. 그리고 was는 오류 page를 뒤짐
                                            // 당연 client accept json이니까 이거 처리하는 에러 핸들러(컨트롤러) 동작
            }

        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null; // 여기걸리면 예외는 계속 전파됨
    }
}
