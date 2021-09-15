package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    // 보통 요청이 오면 view controller 거처서 렌더링 해서 나가야하는데
    // 아래는 그냥 body에 string 찍어서 보내는 정도로 진행
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) { // java에서 제공하는 규약에 따른 http rq를 받을수 있음
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
