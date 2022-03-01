package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) { // 이건 큰의미가 없지 않나? 아.. 아니다.. 일부러 이렇게 했었음 mvc1에서
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키 (브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId())); // 쿠키 값은 String을 넣어야 함
        response.addCookie(idCookie);

        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
        sessionManager.createSession(loginMember,response);

        return "redirect:/";
    }

    // 서블릿 세션은 request객체에 존재. 그래서 response대신 requeset 받음
    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환,  없으면 신규 세션을 생성 ( 쿠키값을 알아서 보는거 겠지?)
            // V2에서는 매번 생성했었음
        HttpSession session = request.getSession(); // true값을 넘겨야 생성이긴 한데, defualt가 true
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // 원래 response의 쿠키에 값을 세팅해줬는데.. 이거 여기서 해주나?

        // 세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
//        sessionManager.createSession(loginMember,response);

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        // response 보낼때 HttpServletResponse말고 뭐있었지? ResponseEntity 같은것은 view resolver로 안들어감.

        expireCookie(response, "memberId");

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 있으면 가져옴. 없으면 null
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // 이렇게 하면 쿠키 삭제됨. ( 아마 브라우저에서. )
        response.addCookie(cookie);
    }
}
