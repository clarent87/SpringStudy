package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


// 특정 패키지 밑의 빈에만 적용 진행하는 beanPostProcessor
@Slf4j
public class PackageLogTraceProxyPostProcessor implements BeanPostProcessor {

    private final String basePackage; // postProcessor 적용할 패키지 명 ( app 패키지 아래만 적용 예정 )
    private final Advisor advisor; // 프록시 생성에 advisor는 필수!

    public PackageLogTraceProxyPostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }


    // 빈 객체가 완전히 초기화 되고 나서, 아래 작업을 진행하기 위해
    // postProcessAfterInitialization를 override함
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        // [*] 여기서 스프링이 등록하는 모든 bean들이 넘어오기 때문에, 어떤 bean들이 등록되는지 볼수도 있음
        log.info("param beanName={} bean={}", beanName, bean.getClass());

        //프록시 적용 대상 여부 체크
        //프록시 적용 대상이 아니면 원본을 그대로 반환
        // [*] 빈의 패키지 네임은 아래 처럼 추출 가능
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basePackage)) {
            return bean;
        }

        //프록시 대상이면 프록시를 만들어서 반환
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);
        Object proxy = proxyFactory.getProxy(); // 따로 캐스팅 안함. 그냥 Object type으로 return하면된다.

        log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());
        return proxy;
    }
}
