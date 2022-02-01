package hello.servlet.web.frontcontroller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class MyView {
    private final String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // foreach 뭐하는지 이해 어렵다고 해서 method로 뽑음
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);

    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        // jsp 템플릿 엔진은 setAttribute에 변수를 넣어야 jsp파일에서 사용할수 있다.
        model.forEach(request::setAttribute); // 람다 대신 method 이름 쓰는 그거 썻음. 이거 다시 study필요 ( method 추론.. )
    }
}
