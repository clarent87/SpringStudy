package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    // CGLIB 동작안하므로,, 싱글톤 보장은 안됨. 즉 객체가 TestBean 그대로 등록됨
    // @Configuration 붙였다면 이걸 상속한 ~~CGLIB~~이 컨테이너에 등록됨
    static class TestBean{

        // setter를 이용한 주입 TEST

        //호출 안됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) { // 컨테이너에 없는 bean
            System.out.println("setNoBean1 = " + noBean1);
        }
        //null 호출
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("setNoBean2 = " + noBean2);
        }
        //Optional.empty 호출
        @Autowired(required = false)
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("setNoBean3 = " + noBean3);
        }
    }
    

}
