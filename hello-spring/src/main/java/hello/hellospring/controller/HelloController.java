package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!"); // model? 음..
        return "hello"; // 기본적으로 templates밑에서 hello.html을 찾음
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template"; // 템플릿 렌더링시 model 객체의 내용을 확인해서 치환하는듯.. 뭐 어떻게 model을 확인하는지는..
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name")String name) {
        return "hello" + name;
    }

    // API방식의 진짜는 아래
    @GetMapping("hello-api")
    @ResponseBody // 기본적으로 json으로 반환 xml 반환도 가능 
    public Hello helloApi(@RequestParam("name")String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // client에 json으로 떨어짐
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
