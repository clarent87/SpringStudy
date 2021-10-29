package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloData {
    // jackson library가 set/getter를 기본적으로 호출함 (물론 다른 옵션도 있긴한다. ) -> 즉 이거 필요

    private String username;
    private int age;
}
