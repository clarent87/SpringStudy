package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); // http body를 bytecode로 받는다.
        String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// 받은 bytecode를 string으로 변환
            // 보통 다 UTF_8 많이씀


        System.out.println("message = " + message);
        response.getWriter().write("ok");
        
        // test는 postman에서 post method, body에서 raq, text를 세팅해서 "hello"를 전송하면된다.
    }
}
