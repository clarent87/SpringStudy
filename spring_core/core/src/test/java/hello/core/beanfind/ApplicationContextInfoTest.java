package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName); // 2번째 param으로 type을 안줬으니 Object가 나옴
            System.out.println("name = " + beanDefinitionName + "Object = " + bean);
        }
    }
    
    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void finaApplicationBeen() {
        // 위 test는 모든 빈이 출력되었는데, 이건 내가 등록한 것만 출력하는것
        
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // 이건 bean의 meta-data를 꺼내는것

            // role은 세가지 주로 ROLE_APPLICATION 씀. -> 내가 등록한 빈들..
            //Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName); // 2번째 param으로 type을 안줬으니 Object가 나옴
                System.out.println("name = " + beanDefinitionName + "Object = " + bean);
            }
        }
    }
}
