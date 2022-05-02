# 스프링 AOP 포인트 컷

## 포인트컷 지시자

- 개요
  - 포인트컷 지시자 소개

- 포인트컷 지시자
  - 포인트컷 표현식은 execution 같은 포인트컷 지시자(Pointcut Designator)로 시작한다.
  - 줄여서 PCD라 한다
  - 여러 종류가 존재
  - > 앞선 예제들의 `@Pointcut("execution(* hello.aop.order..*(..))")` 에서 execution이 PCD임

- 핵심
  - execution 은 가장 많이 사용하고, 나머지는 자주 사용하지 않는다.
  - 따라서 execution 을 중점적으로 이해하자.

> 뒤에서 나올 내용들은 결국 PCD에 대한 내용임

## 예제 만들기 (2)

- 개요
  - 일단 다음 강의들을 위한 예시 만듬

- 포인트
  - `MemberServiceImpl.class.getMethod("hello", String.class);`
    - > 특정 class에서 method 정보를 reflection으로 뽑음

- 포인트2 👍

    ```java
        @Test
        void printMethod() {
            // [*] 그냥 method 정보 찍으면 아래와 같이 나옴
            // [*] execution PCD에 주었던 aspectj 문법이 아래와 매칭되는 거라서 찍어봄
            // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
            log.info("helloMethod={}", helloMethod);
        }
    ```

## execution - 1 (5)

- 개요
  - execution소개
  - exactMatch(),allMatch() 등 코드 작성함

- `execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)`
  - 이거는 다음과 같음
  - `execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)`
  - ?붙은 부분은 생략할 수 있다.
  - 패턴의경우 `*` 같은 패턴을 지정할 수 있다

- 매칭 조건
  - `"execution(public String  hello.aop.member.MemberServiceImpl.hello(String)) "` 를 풀어서 보면 다음과 같음
  - 접근제어자?: public
  - 반환타입: String
    - > ? 가 안붙었으니 필수
  - 선언타입?: hello.aop.member.MemberServiceImpl
    - > package 경로 다들어 와야함
    - > 타입이라는것은 class를 말함
  - 메서드이름: hello
    - > ? 가 안붙었으니 필수
  - 파라미터: (String)
    - > ? 가 안붙었으니 필수
  - 예외?: 생략

- 가장많이 생략한 코드 👍
  - `"execution(* *(..))"`
  - 위 코드는 풀어쓰면 다음과 같음
    - 접근제어자?: 생략
    - 반환타입: *
    - 선언타입?: 생략
    - 메서드이름: *
    - 파라미터: (..)
    - 예외?: 없음

- 메서드 이름 매칭 예시

  ```java
    @Test
    void nameMatchStar2() {
        //메서드 이름 앞 뒤에 * 을 사용해서 매칭할 수 있다.
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
  ```

- `hello.aop.member.*(1).*(2)`
  - 여기서 1번은 타입/class 를 말하고 2번은 method 명임
  - > code랑 같이 보면이해됨
  - > code는 ExecutionTest

- 포인트컷 execution 문법에서 패키지경로는 나타내는 `. , ..` 의 차이를 이해해야 한다
  - `.` : 정확하게 해당 위치의 패키지
  - `..` : 해당 위치의 패키지와 그 하위 패키지도 포함

여기는 코드 참조하는게 좋음. 주석 달아둠

## execution - 2 (9)

- 개요
  - type매칭 소개
  - 파라메터 매칭 소개

- 간단 예시  (이건 당연한 예시)

  ```java
      // type은 class를 말하는거. 보면 정확하게 매칭된다. 
      // MemberServiceImpl.* 에서 .뒤의 *는 method명에 매칭됨
      @Test
      void typeExactMatch() {
          pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
          assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
      }
  ```

- 아래 예시가 중요

  ```java
    pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
    // helloMethod 는 MemberService interface에 있음. 그래서 매칭이 됨 (부모 타입에 매칭 가능)
    // 당연한 말이긴 한데, 만약 helloMethod가 MemberService에 없었다면 false임
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

  ```

- 파라메터 매칭
  - 앞서 사용했던 파라메터의 `(..)`는 모든 파라메터에 매칭된다는것
    - > 즉, 이거는 파라메터가 없는 케이스도 포함된다.
  - `()`는 파라메터가 없다는 의미 --> 코드에서 사용된 모습을 참조하기 바람
  - `"execution(* *(String, ..))"`
    - 요거 이해하면 끝 --> 코드나 pdf 참조 하기 바람

## within (12)

- 개요
  - within PCD 소개
  
- within
  - 해당 타입(class)이 매칭되면 **그 안의 메서드(조인 포인트)들**이 자동으로 매칭된다.
  - 문법은 단순한데 execution 에서 타입 부분만 사용한다고 보면 된
  - > 잘사용 안하는 PCD임

- 주의점! 👍
  - within 사용시 주의해야 할 점이 있다. 표현식에 부모 타입을 지정하면 안된다는 점이다.
  - 정확하게 타입이 맞아야 한다. 이 부분에서 execution 과 차이가 난다.

문법 자체는 쉽네, 말한대로 execution 문법의 타입 부분만 사용함
  
여튼 이거는 잘사용안함. 이거 대신 execution을 쓰게됨. 별이유 때문에 그런건 아니고  
within에 적용할수 있는 문법이 type에만 한정되어서.. 그리고 인터페이스를 대상으로 포인트컷을 걸기도 하는데
execution으로는 되도, within으로는 안됨.. 

## args (14)

- 개요
  - args 소개 
  - ArgsTest 작성함
  - > 근데 within처럼 딱히 중요한 것은 아닌가봄

- args
  - within은 execution 의 타입 부분만으로 매칭하는거라면
  - args는 execution의 param 부분만으로 매칭하는것
    - > 근데 execution의 param 매칭이랑은 차이가 있다. 약간
  
- 기본 문법은 execution 의 args 부분과 같다
- execution과 args의 차이점
  - execution 은 파라미터 타입이 정확하게 매칭되어야 한다. 
  - execution 은 클래스에 선언된 정보를 기반으로 판단한다.
  - args 는 부모 타입을 허용한다. args 는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다
  - > execution의 경우 문법에서 타입(class) 부분은 부모 class로 받는게 가능했음
  - > 근데 param의 경우 Method에 선언된 param type이랑 문법으로 명시한 type이 정확히 일치해야했음
  - > args는 클래스에 선언된 정보를 기반으로 매칭하는게 아니라, 실제 넘어온 객체를 보고 판단하니까, 부모 타입 매칭 가능

- 참고
  - args 지시자는 단독으로 사용되기 보다는 뒤에서 설명할 파라미터 바인딩에서 주로 사용된다.

## @target, @within (17)

## @annotation, @args

## bean

## 매개변수 전달

## this, target

## 정리
