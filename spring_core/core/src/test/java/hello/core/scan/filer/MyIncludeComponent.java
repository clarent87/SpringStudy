package hello.core.scan.filer;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
    // 이게 붙은것은 컴포넌트 스캔에 보함
}
