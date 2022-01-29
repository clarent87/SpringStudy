package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // /hello 호출시 이거 호출됨

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username"); // 쿼리 파라메터 가져옴
        System.out.println("username = " + username);

        // 아래 두줄은 헤더에 들어감
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // http body에 들어감감
       response.getWriter().write("hello " + username);
    }
}
