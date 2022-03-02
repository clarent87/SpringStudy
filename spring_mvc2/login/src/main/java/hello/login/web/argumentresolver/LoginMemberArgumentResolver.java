package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 이거 등록해줘야 동작함
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    // supportsParameter가 매번 호출되는것은 아님. 최초에 호출되면, 그뒤로는 호출이 안됨 ( 내부에 캐시가 있어서 그렇다고 설명함 )
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // 해당 메소스 parameter가 Login 어노테이션이 있는지?

        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType()); // 해당 메소스 parameter가 member class가 맞는지?

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실행");

        // request는 아래 처럼 뽑음
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);
        if (session == null) {
            return null; // 이경우  null이 homeLoginV3ArgumentResolver의 Member 변수에 할당됨
        }
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
