package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()) 같은 동작으로 보임
// 이건 단순히 상태코드만 바꿔주는것! request의 accept에 따라서 BasicErrorControllerr가 return을 json으로 할지 html로 할지 판단해줌.
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
public class BadRequestException extends RuntimeException{ // implement가 아님. extends -> 당연.. RuntimeException 는 class니까.
}
