package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MemberController {

    // controller -> service
    private final MemberService memberService;
    
    // [세터 주입], public 으로 열어 놔야 하는게 단점.
//    private  MemberService memberService;
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

    // 생성자 빼고 아래와 같이 직접 변수에 autowired 붙일수 잇다. -> [필드 주입]
    // final 띠어 버렸음.
    // @Autowired  private  MemberService memberService;

    // 알아서 memberservice를 스프링 컨테이너에서 찾아서 연결해줌
    // 스프링 컨테이너 뜰때 controller 붇은 애들은 contructor 호출해서 생성
    @Autowired
    public MemberController(MemberService memberService) { // DI 해줌.
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm"; // TODO: 모델을 받는 경우는 template값을 치환할때 인가봄, 그리고 String return인게 신기.
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/"; //이건 홈화면으로 보내는것.
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}







