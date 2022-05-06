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

- 중요 사실!! 👍 👍
  - 강좌에 언급이 안되었는데
  - 포인트컷의 접근 제어자는 의미가 없다.
    - <https://docs.spring.io/spring-framework/docs/3.1.x/spring-framework-reference/html/aop.html#aop-introduction-spring-defn>
      - 여기에 As a consequence, any given pointcut will be matched against public methods only! 라고 명시됨
    - 내 생각에 접근제어자 문법은 aspectj꺼 가져오다 보니 그냥 있게 된거 같다.
    - 여튼 Spring AOP는 프록시 패턴 기반으로 구현되다 보니, java 상속의 제약을 벗어날수는 없음
      - 물론 cglib이 어느정도 바이트 코드 조작을 한다고는 하지만.. ( 아마 클래스 상속해서 프록시 클래스 만드는거 정도 일거 같은데.. )
        - <https://taes-k.github.io/2021/04/25/byte-code-instrumentation/>

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

- execution 파라미터 매칭 규칙은 다음과 같다.
  - (String) : 정확하게 String 타입 파라미터
  - () : 파라미터가 없어야 한다.
  - (*) : 정확히 하나의 파라미터, 단 모든 타입을 허용한다.
  - `(*, *)` : 정확히 두 개의 파라미터, 단 모든 타입을 허용한다.
  - (..) : 숫자와 무관하게 모든 파라미터, 모든 타입을 허용한다.
    참고로 파라미터가 없어도 된다. 0..* 로이해하면 된다.
  - (String, ..) : String 타입으로 시작해야 한다. 숫자와 무관하게 모든 파라미터, 모든 타입을 허용한다.
    예) (String) , (String, Xxx) , (String, Xxx, Xxx) 허용

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
  - args 는 부모 타입을 허용한다. args 는 **실제 넘어온 파라미터 객체 인스턴스**를 보고 판단한다
  - > execution의 경우 문법에서 타입(class) 부분은 부모 class로 받는게 가능했음
  - > 근데 param의 경우 Method에 선언된 param type이랑 문법으로 명시한 type이 정확히 일치해야했음
  - > args는 클래스에 선언된 정보를 기반으로 매칭하는게 아니라, 실제 넘어온 객체를 보고 판단하니까, 부모 타입 매칭 가능

- `args(java.io.Serializable): 런타임에 전달된 인수로 판단 (동적)`
  - 이 말은 실제 객체가 넘어와야 포인트컷 true, false 판단 된다는것
  - > 물론 args의 경우 test는 method handle로 가능했지만.

- 참고
  - args 지시자는 단독으로 사용되기 보다는 뒤에서 설명할 파라미터 바인딩에서 주로 사용된다.

## @target, @within (17)

- 앞선 내용 리마인드
  - @Around 는 어드바이스 인데, 이거 종류가 6개쯤 되었음
  - @Around 에 PCD 넣을수 있는데, 이게 싫으면 @Pointcut으로 따로 포인트컷을 빼서 만들수 있음
    - AspectJExpressionPointcut class로 포인트컷 객체 만드는것은, advisor를 직접 만들떄 pointcut, advice를 넣어주는데 썻엇고
      - 이경우 ProxyFactory를 이용했엇지..
    - test code로 강의하기 위해서도 썻음
  - 어드바이스는 적용 순서가 기본적으로 없음
    - @Order로 순서 적용할수는 있는데, 이경우 class단위로만 가능
  - @Aspect는 기본적으로 bean등록이 안됨. 따라서 직접 등록을 해주어야한다.
    - > 원리는 bean 후처리기 와 같다고 하였고, proxy project에서 빈 후처리기를 통한 원리를 보임
    - > 근데 이거 실제 작동 방식은 AspectAop 참조
  - **class를 type이라고도 함** 그래서 어노테이션 만들때 `@Target(ElementType.TYPE)` 를 붙이면 class를 target으로 한다는의미
    - > @target 과 @Target 은 다름. 앞에껀 PCD도 뒤에껀 어노테이션

- 개요
  - 앞선 within은 과 현재 배울 @within 모두 PCD 문법근데 쫌 다름
    - @within은 소문자로 시작하는거 주의
  - @target, @within 모두 PCB처럼 사용되는 문법임 ( 어노테이션 아님)
  - @target , @within 은 타입에 있는 애노테이션으로 AOP 적용 여부를 판단한다.
  - > 이것도 생각보다 많이 사용되지는 않는가봄

- 포인트
  - @target, @within
  - 예시
    - `@Around("@within(hello.aop.member.annotation.ClassAop) ")`
    - @ClassAop 어노테이션이 걸린 class 안의 모든 메소드가 조인포인트 대상이 됨
      - > 즉, 포인트컷에 의해 걸린다는 것
  - @target의 경우 인스턴스의 모든 메소드 (부모 메소드 포함)가 조인포인트 대상이 되는거고
    - > 즉 @ClassAop가 붙은 Child class가 있다면, 해당 클래스의 부모 클래스 메소드 모두 조인포인트가 됨 (즉 어드바이스 붙는다는것)
  - @within인 ClassAop가 붙은 class만 조인포인트 대상이 됨

- 포인트2
  - 앞선 강좌의 args, within은 포인트컷 객체만 만들어서 match method로 쉽게 test가능했는데
    - > 이때도 method handle은 있었음. 근데 이렇게 test를 했다고 정적인 정보로 pointcut이 적용된다고 생각하는 것은 오산 -> args 처럼..
  - @target, @within의 경우 실제 객체가 있어야 기능을 보여 줄수 있어서, target이 될 bean까지 만들어서 test를 하였다.
    - > 그냥 단순히 Annotation을 class대상으로 걸어야 해서 bean을 만든게 아닌가 싶네.
    - @target의 경우 인스턴스 기준 -> 즉 인스턴스에 있는 모든 메소드가 조인 포인트
    - @within의 경우 정적인 클래스 정보만 보고 판단 -> 즉 어노테이션 걸린 class의 메소드만 조인 포인트
  - > 크게 중요하지는 않음

- 참고
  - @target , @within 지시자는 뒤에서 설명할 파라미터 바인딩에서 함께 사용된다.

- 주의
  - 다음 포인트컷 지시자는 단독으로 사용하면 안된다. `args, @args, @target`
  - > pdf내용도 쫌 모호한데
  - > 정리하자면 위 PCD같은경우, 위 PCD에 의해 어드바이스가 적용되었는지 아닌지는 실행 시점에만 알수 있음
    - > args는 아니지 않나?
  - > 따라서 AnnotationAwareAspectJAutoProxyCreator(자동 프록시 생성기) 에서 포인트 컷으로 프록시를 만들지 말지 결정해야하는데,
    - > 빈후처리기.md 내용
  - > 위에 것들은 모두 true로 판단해서 스프링에 올라오는 모든 bean을 프록시로 만들려고 함. 이때 final 이 붙은 class bean이 대상이 되면
  - > 오류가 발생한다.

여튼, `args, @args, @target` 는 런타임에 넘어온 인수/객체가 있어야 한다는 사실만 기억하면 될거 같다.  

- 의견
  - > 추가적으로 @target, @within은 target, within PCD인데 아마 annotation 붙은걸 대상으로 한다는 의미 같다. args, @args도 같은 개념 같음

- Q/A 👍
  - @SpringBootTest 를 사용하면서 그안 inner 클래스에  @Configuration를 붙여서 bean을 등록했더니 AOP가 적용되지 않는다..?
    - @Configuration을 사용하게 되면 스프링 부트의 다양한 설정들이 먹히지 않고, @Configuration 안에서만 적용한 설정이 먹히게 됩니다.
    - 이 설정이 우선권을 가지고 가버리기 때문에 스프링 부트의 다른 설정이 적용되지 않습니다.
    - 쉽게 이야기해서 테스트에서는 이 설정만 사용하게 됩니다.
    - 여기서 문제는 스프링 부트가 만들어주는 AOP 관련 기본 클래스들도 스프링 빈으로 등록되지 않고,
    - 결국 AOP 자체가 동작하지 않게 된다는 점입니다. (강의에서 말씀드린 AutoProxy가 있어야 AOP를 적용할 수 있습니다.)
    - 그러면 AOP가 동작하도록 하는 기능들을 등록하면 되겠지요?
    - 바로 @EnableAspectJAutoProxy를 다음과 같이 직접 넣어주시면 됩니다.
    - 그러면 AOP가 적용되면서 정상 동작하게 됩니다

    ```java
    @EnableAspectJAutoProxy
    @Configuration
    static class InnerConfig {
    }
        
    ```

    - 그런데 이렇게 사용하는 것은 많이 번거롭습니다.
    - 스프링 부트는 스프링 부트의 기본 기능을 설정을 사용하면서 그 위에 원하는 설정을 더하는 방법도 제공합니다.
    - 바로 @TestConfiguration을 사용하면 됩니다.

    ```java
    @TestConfiguration
    static class InnerConfig {
    }
    ```

## @annotation, @args (20)

- 리마인드
  - @Aspect - @Around로 proxy 생성
    - @Aspect 붙인 class는 빈등록을 해줘야함.
      - 자동 프록시 생성기(빈후처리기) 가 이거랑 Advisor 빈들 을 처리해줌
    - @Around에는 포인트컷내용(PCD)를 작성해주고, method body에 advice 내용을 작성
    - 이떄 advice의 반환은 Object 이다.
      - @Around 외 나머지 5개 어드바이스 만들땐 return이 void임.

- 개요
  - @annotation 는 메소드에 걸린 annotation을 보고 판단하는 PCD
  - 앞선 강좌의 @target, @within 는 클래스에 걸린 어노테이션을 보고 판단하는 거였음
  - > **이건 가끔 쓰인다고 함**
  - > 해보니 간단함
  - > @Async가 이런 원리 아닐까?

- @args 는 예제 없이 설명만 진행
  - `@args(test.Check)`
    - 전달된 인수의 런타임 타입에 @Check 애노테이션이 있는 경우에 매칭한다.
    - 즉, 런타임시 `MemberService.hello(ClassA)` 란 메소드를 호출한경우 파라메터인 ClassA에 @Check가 붙어있으면 매칭되서 호출됨
  - > 잘 안씀

## bean (23)

- 개요
  - **스프링 빈의 이름**으로 AOP 적용 여부를 지정한다. 이것은 스프링에서만 사용할 수 있는 특별한 지시자이다
  - 즉 aspectj 같은덴 있는게 아님

- 포인트
  - 아래와 같이 씀
  - > 근데 역시 잘 쓰는건 아니라고 함
  - `@Around("bean(orderService) || bean(*Repository)")`

## 매개변수 전달 (24)

- 개요
  - 어드바이스에 매개변수 전달하는 방법
    - > 근데 method 호출에 쓰는 param은 ProceedingJoinPoint 로 받을수 있찌 않나? getArgs method지원되는데..
    - > 맞음 강의에서도 나옴
  - > 중요하다고 함
  - args PCD 사용법이랑 다시 비교해보면서 보는게 좋음

- 중요 포인트
  - PCD마다 전달 받을수 있는 값이 다름
  - args 로는 진짜로 param을 전달 받을수 있고 ( 물론 그래도 args PCD는 적용됨)
  - this, target으로는 현재 객체를 받을수 있음 ( this는 프록시 객체 받고, target은 실제 객체 )
  - @annotation으로는 target method에 적용된 어노테이션을 받아올수 잇음
  - @target, @within으로는 현태 target class에 적용된 어노테이션을 받아올수 잇음

- 중요 포인트 👍
  - ProceedingJoinPoint 로 param을 꺼낼수 있는데 이러면 **가독성이 좋지가 않다.**
  - 그래서 포인트컷 매개변수 전달을 이용함
  - > 단 모든 PCD에서 param을 꺼낼수 있는건 아닌듯.
  - > 물론 그냥 포인트컷으로 매개변수를 받고 싶다면, PCD && args(arg,..) 찍고 type을 Object로 받으면 되긴 할듯.

  ```java
  @Around("allMember()")
  public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
      // [*] 역시 생각한 대로 joinPoint를 이용해서 param값 가져올수 있네
      // [*] 근데 이렇게 뽑는거는 가독성이 좋지가 않다.
      Object arg1 = joinPoint.getArgs()[0];
      log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg1);
      return joinPoint.proceed();
  }
  ```

- 포인트
  - 여기서 this와 target도 예시로 일부 보여줌

- 리마인드 🥉
  - spring은 proxyFactory를 만들때 interface 기반 class가 target이어도 cglib을 이용한다고 했음
  - 여기 예시인 MemberServiceImple도 interface 기반인데, 실제 프록시 만들어진것은 아래와 같다
    - `class hello.aop.member.MemberServiceImpl$$EnhancerBySpringCGLIB$$99539c87`
    - 즉, MemberServiceImpl 를 상속해서 만들었다고 봐야 함
    - 실제로 superclass를 찍어보니 MemberServiceImpl가 나옴
  
여기는 중요한 point들이 많음 ParameterTest class에 주석 많이 달아두었으니 참조 바람  

- 포인트2
  - **아래처럼 어노테이션 value 꺼내는거 종종 쓸만하다고 함**

  ```java
   @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
        }
  ```
  
- 요약
  - 보면 PCD에 넣었던 type 대신 변수명을 쓰는형태, 이때 해당 변수의 type은 메소드를 보고 결정됨 ( 같은 이름의 param의 타입으로..)

다음 절의 this, target은 잘 쓰지는 않는데, 내용이 복잡하다고 함

## this, target (28)

- 리마인드
  - 타입이란 ? class나 interface를 지칭

- 개요
  - this, target 설명
  - > 중요한건 아닌데 어려움, 근데 공부하다보면 메뉴얼에 종종 나와서 소개

- 정의
  - this : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트
  - target : Target 객체(스프링 AOP 프록시가 가르키는 실제 대상)를 대상으로 하는 조인 포인트
  - this , target 은 다음과 같이 적용 **타입 하나를 정확하게 지정**해야 한다.

    ```java
      this(hello.aop.member.MemberService)
      target(hello.aop.member.MemberService)
    ```

  - `*` 같은패턴을사용할수없다.
  - 둘다 **부모 타입을 허용한다.**
    - > 이건 target의 부모 타입을 명시해도 조인포인트로 걸린다는것.
    - > @target이랑 마찬가지로 해당 class의 모든 메소드가 조인포인트 대상이 될듯

- 중요 포인트
  - 스프링은 JDK 동적 프록시와 CGLIB를 선택 가능하다
    - > 근데 기본적으로 CGLIB만 이용한다는거 같았는데..
    - 그래서 this, target의 적용 여부가 각각 다르다.

- 중요 포인트 2
  - pdf 30 페이지
  - MemberService 인터페이스 지정 this(hello.aop.member.MemberService) : proxy 객체를 보고 판단한다.
    - this 는 부모 타입을허용하기 때문에 AOP가 적용된다.
    - > 해당 페이지를 보면 proxy는 MemberServiceImple을 상속받아 구현되어 있음을 알수 잇따 -> cglib이라서
    - > 근데 MemberServiceImple은 MemberService interface를 imple하니까 aop가 적용되는것 -> 즉, 부모타입을 허용한다고 했으니...

- 중요 포인트 3 👍
  - application.properties 에 세팅
    - spring.aop.proxy-target-class=true  // CGLIB
    - spring.aop.proxy-target-class=false // JDK 동적 프록시
    - > 기본적으로 최신 spring-boot는 CGLIB만 동작 -> 왜 스프링이 이런 결정을 했는지는 뒤 쪽에서 강의한다고 함
    - > 당연한 말인데 false일때 target이 구체 클래스면 당연히 CGLIB이 동작
  - 테스트에선 아래와 같이 세팅
    - `@SpringBootTest(properties = "spring.aop.proxy-target-class=false") //JDK 동적 프록시`
    - `//@SpringBootTest(properties = "spring.aop.proxy-target-class=true") //CGLIB`

- 요약
  - this는 프록시 bean을 대상으로 조인포인트를 걸기때문에, 프록시 bean이 어떤 형태냐에 따라 걸리고 안걸리고 함
    - proxy가 jdk 프록시 였을때 -> interface기반으로 프록시 객체가 만들어 지므로, 해당 interface를 impl한 target class로는 걸리지 않음
    - proxy가 cgbli 프록시 일때 -> interface를 impl한 target class를 상속해서 프록시 bean을 만듬. 따라서 interface를 impl한 target class로도 걸림
  - target은 실제 target class를 대상으로 조인포인트를 걸기 때문에, 위와 같은 이슈가 없음

- 참고
  - this , target 지시자는 단독으로 사용되기 보다는 파라미터 바인딩에서 주로 사용된다.

- 정리
  - 프록시를 대상으로 하는 this 의 경우 구체 클래스를 지정하면 프록시 생성 전략에 따라서 다른 결과가 나올 수 있다는 점을 알아두자.

- Q/A
  - 테스트 결과를 보면 `@Around("this(hello.aop.member.MemberService)")` 이경우 proxy의 타입에 따라서 동작이 결정되는데
  - 그렇다면 위 포인트컷의 경우 일단 해당하는 bean을 모두 proxy 객체로 만드는 것인가?
    - 포인트컷으로 일단 프록시를 만들지 안만들지 결정하는데 사용함.
    - > 근데 항상 ture인 포인트컷은 모든 bean에 걸리기 때문에 오류나는데.. 위와 같은 경우는 MemberService 랑 매칭되는 모든 bean을 프록시로 만드나봄
  - > 답변은 잘 모르겠지만 그런거 같다고함

## 정리
