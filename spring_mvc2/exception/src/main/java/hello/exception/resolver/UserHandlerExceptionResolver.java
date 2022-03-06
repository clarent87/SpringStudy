package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {

            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                String acceptHeader = request.getHeader("accept"); // request가 json인 경우 아닌경우를 나누어 예외처리 하기 위함
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 응답에 400 상태 세팅

                if ("application/json".equals(acceptHeader)) { // "application/json" 는 상수로 뽑거나, 다른데 정의된거 쓰는게 좋다함 ( 아마 properties? )
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result = objectMapper.writeValueAsString(errorResult); // map을 json string으로 만들어주네

                    // ModelAndView 반환이라 어쩔수 없다. response일일이 세팅해야한다.
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    return new ModelAndView(); // was까지 내려가고 끝남. -> 즉 controller로 예외 처리를 위해 올라가지 않음
                                                // MyHandlerExceptionResolver는 sendError를 먹여서 was에서 다시 올라온 케이스..
                } else {
                    // TEXT/HTML
                    return new ModelAndView("error/500"); // error/500.html 이 호출되는거. view resolver에 의해
                                                    // 근데 model로 에러 data를 안넘겨서 500.html의 오류 정보는 모두 null값이 나옴
                }
            }

        } catch (IOException e) {
            log.error("resolver ex", e);
        }

        return null;
    }
}
