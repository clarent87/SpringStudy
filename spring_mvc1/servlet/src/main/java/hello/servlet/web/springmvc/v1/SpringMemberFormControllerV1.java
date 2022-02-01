package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//@Component //컴포넌트 스캔을 통해 스프링 빈으로 등록
//@RequestMapping
@Controller
public class SpringMemberFormControllerV1 {

    // 아래 RequestMapping에 대한 내용은 pdf에만 나옴
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() { // param은 없는게 신기
        return new ModelAndView("new-form");
    }
}
