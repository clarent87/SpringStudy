package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // controller -> service
    private final MemberService memberService;

    // 알아서 memberservice를 스프링 컨테이너에서 찾아서 연결해줌
    // 스프링 컨테이너 뜰때 controller 붇은 애들은 contructor 호출해서 생성
    @Autowired
    public MemberController(MemberService memberService) { // DI 해줌.
        this.memberService = memberService;
    }
}
