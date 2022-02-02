package hello.servlet.web.springmvc.v2;

import hello.servlet.domain.mamber.Member;
import hello.servlet.domain.mamber.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 *클래스 단위->메서드 단위
 * @RequestMapping 클래스 레벨과 메서드 레벨 조합
 * */
@Controller  // 아래 RequetMapping을 썻으니 여기는 component 써도 될듯
@RequestMapping("/springmvc/v2/members") // 아래 매핑에서 공통부분을 빼냄.
public class SpringMemberControllerV2 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/new-form")
    public ModelAndView newForm() { // param은 없는게 신기
        return new ModelAndView("new-form");
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        System.out.println("member = " + member);
        memberRepository.save(member);
        ModelAndView mv = new ModelAndView("save-result");
        mv.addObject("member", member); // ModleAndView에서 제공하는 api
        return mv;
    }

    // /springmvc/v2/members에 걸린다.
    @RequestMapping
    public ModelAndView members() {

        List<Member> members = memberRepository.findAll();

        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;

    }
}
