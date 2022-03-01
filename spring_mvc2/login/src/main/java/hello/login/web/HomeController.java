package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

    //Cookie는 httpservletRequest에서도 뽑을수 있음. 가장 쉽게 CookieValue를 이용함
    // 쿠키값은 String인에 Long으로 스프링이 타입 컨버전해서 넣어줌. 해당 내용은 나중에 소개
//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if (memberId == null) {
            // 일반 홈
            return "home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        // 쿠키가 왔을땐 자동 로그인 loginHome
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        // 기존처럼 쿠키를 직접 받지 않는다. 만든 sessionManager가 request를 param으로 받기때문


        // 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }
}