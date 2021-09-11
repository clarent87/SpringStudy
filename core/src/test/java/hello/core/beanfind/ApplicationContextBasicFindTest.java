package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
//        System.out.println("memberService = " + memberService);
//        System.out.println("memberService.getClass = " + memberService.getClass());

        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

    }


    @Test
    @DisplayName("이름없이 타입으로만 조회")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class); // 이거 장단이 있다. 같은 type이 여러게 등록되어 있을수도 있나봄. .
                                                                        // 보면 type은 인터페이스를 넣어줬네..
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    @Test
    @DisplayName("concrete type으로 조회")
    void findBeanByName2() {
        // 이 코드는 별로 좋은 코드는 아님.. 왜냐명 getBean이 concrete class에 의존.. (MemberServiceImpl.class)
        // 즉 역할에 의존하는 코드가 좋다.

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByNameX() {
        // JUnit5에 와서 Exception 처리하는게 지저분해짐..
        // assertThrows는 junit에 있는거,  assertj가아니라..
        // 아래 코드는 람다 수행시 NoSuchBeanDefinitionException 예외가 나와야 한다는것
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxx", MemberServiceImpl.class));  // Executable이네.. supplier같은게 아니라..

    }
}
