package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // spring boot는 기본적으로 jackson lib 이 있음
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);
        // test는 postman에서 body, raw 선택후 Json선택.
        // messageBody = { "username" :"hello", "age":20} 이라고 console에 찍힘, 어짜피 json받은것도 문자열이기 때문에..
        // library는 이 문자열을 parsing해서 객체로 변환해 주는것.

        // 읽은 json을 jackson을 이용해서 객체로 변환은 다음과 같다.

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
            // method 만들때 .class를 넣어주는 pattern.. 이게 java 8에서는 뭔가 달라지지 않았나? -> 추론때문에 빼도되는?
            // 혹은 generic.

        System.out.println("helloData.name = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}
