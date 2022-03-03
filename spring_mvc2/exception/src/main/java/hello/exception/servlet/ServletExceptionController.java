package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 여튼 어기 있는 모든 error는 was 기본 오류 page가 나옴.
@Slf4j
@Controller
public class ServletExceptionController {
    @GetMapping("/error-ex")
    public void errorEx() {
        throw new RuntimeException("예외 발생!"); // /error-ex페이지 접근시 status 500 에러가 뜸.(문구도 그렇게 나옴)
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!"); // 상태코드랑 message 추가가능
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400, "400 오류!"); // 지금은 이런 세팅한 message가 오류 page에서 보이진 않는데. 보이게 할수 있다함
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500);
    }
}
