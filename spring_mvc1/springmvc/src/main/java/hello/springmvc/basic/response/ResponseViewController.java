package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!"); // 원래 servlet에서는 request.setAttribute()로 넘겼었음
                                                                           // 뷰템플릿의 data가 hello!로 치환됨
        return mav;
    }

    // 이방식의 응답을 권장
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) { // model만 받는형태도 프론트컨트롤러 만들때 나왔었음. Model은 argument resolver 로 지정해둔 타입 외일듯..
        model.addAttribute("data", "hello!!");
        return "response/hello"; // Controller에서 그냥 string 반환이면 view의 논리적 이름이 된다. ( 앞에서 나왔었음 )
    }

    // 아래도 가능한데, 권장하지는 않음. 너무 불명확함.
    // 아래와 같은 경우 논리 path는 mapping된 url과 같은 "response/hello"가 된다. 앞에 slash는 빼고
    // 근데 이렇게 동작하는 조건이 있었음. ( 주로 response 객체가 변형되지 않을떄.. )
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!!");
    }

}
