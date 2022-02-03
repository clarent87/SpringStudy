package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
//@RestController
public class ResponseBodyController {

    // 응답의 다양한 형태를 정리하는 차원에서 코딩

    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
        // HttpServletResponse 는 서블릿에서 제공하는 type, 이거 response body를 직접 작성하는 형태임
    }

    /**
     * HttpEntity, ResponseEntity(Http Status 추가)
     *
     * @return
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    // helloData를 json으로 변경해서 전달
    // 이거 client의 accpet에 영향 받는다고 했었음
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return new ResponseEntity<>(helloData, HttpStatus.OK); // HttpEntity와는 다르게 상태를 넣어줄수 있는것이 특징
                                                                // ResponseEntity도 Writer로 body에 값을 직접 쓰는거랑 동일하긴 했음
                                                                // 이거로 view 만들려면 html을 스트링으로 써서 넘기는 노가다 필요
    }

    // ResponseEntity대신 어노테이션 방식으로 진행. 이경우 상태 코드 어노테이션은 따로 써줘야함
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}
