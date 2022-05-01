package hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 아래 어노테이션을 클래스에 붙일때 쓰는거라고 함
@Retention(RetentionPolicy.RUNTIME)
public @interface ClassAop {
}
