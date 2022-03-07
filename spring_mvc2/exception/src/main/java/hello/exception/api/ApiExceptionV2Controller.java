package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

//    // ApiExceptionV2Controller 컨트롤러의 IllegalArgumentException 예외는 이게 처리함
//    // RestController 이기 때문에 ErrorResult는 json으로 반환됨
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ErrorResult illegalExHandler(IllegalArgumentException e) {
//        log.error("[exceptionHandler] ex", e);
//        return new ErrorResult("BAD", e.getMessage());
//            // 이거 기본적으로 정상 흐름이라서 status 200이 반환됨.
//            // 그래서 ResponseStatus 를 이용해서 status 세팅해주는것이 필요
//    }
//
//    // status를 ResponseEntity로 추가하는 예제
//    // @ExceptionHandler(UserException.class) 이거 param을 생략가능. method의 param type이랑 같다면..
//    @ExceptionHandler
//    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
//        log.error("[exceptionHandler] ex", e);
//        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
//        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST); // ResponseEntity 만드는 방법은 사실 여러가지..
//
//    }
//
//    // 그냥 Exception 처리할때  예제
//    // Exception의 자식들은 여기 다걸림. 단 IllegalArgumentException, UserException 의 자식은 위 exhandler에서 처리됨
//    // 즉, 그외 나머지 예외는 여기 다 걸림
//    // 주의할 점은 @ExceptionHandler는 지금 controller에서 발생하는 예외만 관여한다.
//    // (당연.. handler 정보를 바탕으로 ExceptionHandlerExceptionResolver가 처리 하는 거니까 )
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler
//    public ErrorResult userExHandler(Exception e) {
//        log.error("[exceptionHandler] ex", e);
//        return new ErrorResult("EX", "내부 오류");
//    }


    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
