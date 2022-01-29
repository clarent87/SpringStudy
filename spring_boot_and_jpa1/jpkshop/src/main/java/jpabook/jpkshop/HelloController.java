package jpabook.jpkshop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // 모델에 값을 세팅해서 뷰로 넘길수 있다.
        return "hello"; // 화면이름.. hello.html.. resource/templates에 있는거
    }
}
