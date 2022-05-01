# 스프링 AOP 구현

## 프로젝트 생성

- 롬복만 추가해서 만듬
- `implementation 'org.springframework.boot:spring-boot-starter-aop'`
  - spring-data-jpa 같은거 dependency를 넣었다면 aop관련된건 자동으로 들어옴.
  - 근데 그런거 없을경우 위처럼 추가 필요. aspectjweaber가 들어 왔다면 성공

- 포인트
  - 웹 애플리케이션이 아니라서 톰캣같은거는 없다. 즉 실행하면 로그 찍고 종료됨.
  - > 근데 내 cov는 왜 돌리면 계속돌지? 스케쥴러 때문?

## 예제 프로젝트 만들기 (3)

- 개요
  - 예제 프로젝트 만듬
  - 그리고 AopTest 만듬

- 포인트 👍
  - `AopUtils.isAopProxy(orderService)` 로 aopproxy가 적용되었는지 확인 가능
  - > 이거 앞에서도 한번 나왔던거 같음. 그리고 이거로는 직접 만든 proxy는 체크 안됨

## 스프링 AOP 구현 1 - 시작 (6)

- 포인트
  - 스프링 AOP를 사용할 때는 @Aspect 애노테이션을 주로 사용하는데,
  - 이 애노테이션도 AspectJ가 제공하는 애노테이션이다.
    - > pdf에 이런 말이 있는데, 이건 spring 이 aspectj의 껍데기(interface, annotatino)등을 가져와서 쓴다는것
    - > `org.aspectj.lang.annotation.Around;` 같은 import문을 보면 aspectj꺼라는걸 알수 있음
  - @Aspect 를 포함한 org.aspectj 패키지 관련 기능은 aspectjweaver.jar 라이브러리가 제공하는 기능이다.
  - 앞서 build.gradle 에 spring-boot-starter-aop 를 포함했는데,
  - 이렇게 하면 스프링의 AOP 관련 기능과 함께 aspectjweaver.jar 도 함께 사용할 수 있게 의존 관계에 포함된다

- 중요 포인트
  - 앞서 나온 얘기 지만 @Aspect 붙은 class를 빈으로 등록해줘야 aop가 동작
  - `@Import(AspectV1.class) //추가`
    - 테스트 코드에 이렇게 추가했는데 이러면 AscpectV1이 빈등록이 된다
    - 보통 import는 configuration 등록에 썻음.

- 포인트
  - `joinPoint.getSignature()`의 결과
    - `String hello.aop.order.OrderRepository.save(String)`

- 포인트2
  - @Aspect 는 애스펙트라는 표식이지 컴포넌트 스캔이 되는 것은 아니다
  - **스프링 빈으로 등록하는 방법** 👍
    - @Bean 을 사용해서 직접 등록
    - @Component 컴포넌트 스캔을 사용해서 자동 등록
    - @Import 주로 설정 파일을 추가할 때 사용( @Configuration )

## 스프링 AOP 구현 2 - 포인트컷 분리 (10)

- 개요
  - @Around에 포인트 컷 넣었던것을 분리함
  - AspectV2 class에 해당 내용이 있음 -> 주석 있으니 참조 바람

- 포인트
  - `@Pointcut(~)` 로 @Around의 포인트컷을 분리해냄
  - 이렇게 분리한 포인트컷은 재사용 가능
  - 그리고 method명으로 의미 부여가능
    - > 예시에서는 allOrder라는 method명으로 포인트컷을 지칭하게 함

- 포인트2
  - 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처(signature)라 한다.
  - 메서드의 반환 타입은 void 여야 한다.
  - 코드 내용은 비워둔다

- 의문  
  - > 그리고 포인트컷에 param 넣는거 나중에 나온다고 했음
  - > 이거 여기 내용이었나?

## 스프링 AOP 구현 3 - 어드바이스 추가 (12)

- 개요
  - 트랜잭션 기능을 추가
  - 조금더 복잡한 예제를 한다
  - 포인트컷을 어드바이스에 여러개 추가한 예제
    - > proxy 만들떄 하나의 proxy에 여러 어드바이저가 붙을수 있다는거랑은 상관없다.
  - AspectV3에 만듬

- 포인트
  - 보통 트랜잭션은 service 계층에 붙인다고 한다. ( 즉, service class에 적용)

- 포인트2
  - `@Around("allOrder() && allService()")`
    - && (AND), || (OR), ! (NOT) 3가지 조합이 가능

- 포인트3
  - `@Pointcut("execution(* *..*Service.*(..))")`
    - 여기서 포인트컷은 class, interface에 모두 적용이 됨
    - 타입 이름 패턴이 *Service 를 대상으로 하기 때문

- 포인트4
  - 근데 적용된 aop의 순서를 바꾸려면 어떻게 해야할까?
    - 예시에서는 log가 먼저 출력되고 그다음 tx가 호출되었음
    - > 일단 이건 다음에 알아본다

다음절에서는 aop 순서알아보기 이전에 포인트컷을 외부로 빼서 개발하는 것을 알아본다.

- Q/A
  - Aspect안에 around로 작성한 advice가 여러개 인경우 @Aspect Advisor 빌더 내부에 몇개의 어드바이저가 생성되는가?
    - > 답변 모호하게 나옴.
    - > 어드바이저가 여러개 생성되는게 맞을거 같긴함 --> 스프링 AOP 구현 6 - 어드바이스 종류에도 비슷한 Q/A가 있음
    - > 예를들어 @Aspect class가 두개가 있고 각각 advice가 두개가 있을때, 각각에서 하나씩 advice가 매칭되는 target이 있다면?
      - >@Aspect단위로 advisor를 만드는것보다 @Around 단위로 advisor가 만들어 지는것이 효율적 일듯

## 스프링 AOP 구현 4 - 포인트컷 참조 (15)

- 개요
  - 다음과 같이 포인트컷을 공용으로 사용하기 위해 별도의 외부 클래스에 모아두어도 된다.
  - 참고로 외부에서 호출할 때는 포인트컷의 접근 제어자를 public 으로 열어두어야 한다

- 포인트
  - @Aspect class에서 따로 class만들어서 포인트컷을 뺀경우..
  - @Around에서 사용하려면 해당 포인트컷 method의 full 경로는 넣어야함. 즉, 패키지 경로 포함필요

- q/a
  - Pointcust 클래스를 빈으로 등록하니깐 @Around("Pointcuts.allOrder()") 이렇게 해도 되는데 왜 강의에서는 경로를 지정해서 한건가요?
  - > Pointcuts 를 빈으로 등록하지 않아도 아마도 같은 패키지에 있어서 성공하셨을 거에요.
  - > 저도 정확히는 모르겠지만, 같은 패키지인 경우에는 예외적으로 허용이 되는 것 같아요

## 스프링 AOP 구현 5 - 어드바이스 순서 (18)

- 개요
  - 어드바이스 순서 세팅법
  - AspectV5Order만듬
  - @Order를 이용했고, 이건 class level로 붙일수 있음

- 포인트
  - **어드바이스는 기본적으로 순서를 보장하지 않는다.**
  - 순서를 지정하고 싶으면 @Aspect 적용 단위로 org.springframework.core.annotation.@Order 애노테이션을 적용해야 한다.
  - 문제는 이것을 어드바이스 단위가 아니라 클래스 단위로 적용할 수 있다는 점이다.
  - 그래서 지금처럼 하나의 애스펙트에 여러 어드바이스가 있으면 순서를 보장 받을 수 없다. 따라서 애스펙트를 별도의 클래스로 분리해야 한다.

- 포인트2
  - @Aspect 안의 어드바이스 들은 순서 보장이 안됨
  - @Aspect 만 순서 보장이 됨
  - > @Order를 이용해서 순서를 세팅하는건데, 이게 Aspect에만 적용되기 떄문, 따라서
  - > Aspect안의 advice간 순서를 맞추려면 예제처럼 각각 class로 빼야함.

- 의견
  - @Order(2) 이거 bean 등록에도 쓰던거 아닌가?? --> 확인 필요

- Q/A
  - @Order가 적용 된 Aspect와 적용되지 않은 Aspect를 섞어서 사용할 때는 순서가 어떻게 적용 되는 지 문의 드립니다.
  - > 이런 경우는 스프링 메뉴얼에 명확하게 명시가 되어 있지 않습니다. 따라서 순서를 보장하지 않는다고 이해하시면 됩니다.

## 스프링 AOP 구현 6 - 어드바이스 종류 (20)

- 개요
  - 어드바이스 종류 소개
  - 여기까지 알면 aop는 다 알게 되는것 ( 남은건 포인트컷 정도..)
  - AspectV6Advice 작성

- 어드바이스 종류
  - @Around
    - 메서드 호출 전후에 수행, **가장 강력한 어드바이스**, 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능
    - > 사실 나머지 기능은 Around의 기능을 조각내서 사용할수 있게 준비 된것 -> 코드 보면 안다.
  - @Before
    - 조인 포인트 실행 이전에 실행
    - 이거 사용하면 @Before 어드바이스 호출후 joinpoint (즉, target)은 자동으로 호출된다.
    - `JoinPoint joinPoint` 파라메터를 받도록 advice를 만들었는데, 이거 필요 없으면 파라메터 없이 advice만들어도 된다.
    - `@Before("hello.aop.order.aop.Pointcuts.orderAndService()")`
      - > 당연히 Around 때처럼 annotation 안에는 포인트컷 넣어야함
  - @AfterReturning
    - 조인 포인트가 정상 완료후 실행

    ```java
    // returning = "result" 이므로 method의 result 변수에 target의 반환값이 담겨서 온다.
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        // result값 조작은 불가능.
        // 무슨말이냐면 target의 리턴값을 딴걸로 치환해서 반환할수는 없다는 말
        // 단. result를 조작할수는 있음. ( ex. result 객체 안에 변수가 있고 setter가 지원되면 해당 필드를 변경할수는 있음)
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }
    ```

  - @AfterThrowing
    - 메서드가 예외를 던지는 경우 실행
  - @After
    - 조인 포인트가 정상 또는 예외에 관계없이 실행(finally)

- 포인트
  - ~~이해 할때 나머지 어드바이스는 @Around의 try문 않에 있다고 생각하는게 좋음. 특히 After..~~
    - After의 경우 Around의 finally 위치에서 호출되는것은 아님. 로그를 보니 try body이긴 함. 물론 proceed에서 예외 나와도
    - 호출되긴 하겠지만 finally 처럼
  - > 이거 아래 Aspect 안에서 advice가 걸리는 순서를 이해하면 외이런지 쉽게 알수 있음

- 포인트2
  - 호출 결과
    - [around][트랜잭션 시작] void hello.aop.order.OrderService.orderItem(String)
    - [before] void hello.aop.order.OrderService.orderItem(String)
    - [orderService] 실행
    - [orderRepository] 실행
    - [return] void hello.aop.order.OrderService.orderItem(String) return=null
    - [after] void hello.aop.order.OrderService.orderItem(String)
    - [around][트랜잭션 커밋] void hello.aop.order.OrderService.orderItem(String)
    - [around][리소스 릴리즈] void hello.aop.order.OrderService.orderItem(String)

**pdf에 내용이 자세하니 그걸 참조하는게 좋음**

- @AfterReturning
  - returning 절에 지정된 타입의 값을 반환하는 메서드만 대상으로 실행한다.( 부모타입 지정시 자식타입도 인정됨)
    - > 이거 처음보면 무슨말인가 싶음

    ```java
      // 현재 아래 advice의 target은 public void orderItem(String itemId) 이다
      // returning 절에 지정된 타입의 값은 String인데 target method는 void return이므로, 어드바이스 적용 대상에서 제외됨
      // 이경우 적용하고 싶다면 Object result로 해주면 된다.
      // [*] intellij의 경우 advice옆에 icon으로 해당 어드바이스가 적용되는 method를 보여주는데, target이 void인경우는, 잘 인식하지 못하는듯
      @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
      public void doReturn(JoinPoint joinPoint, String result) {
          log.info("[return] {} return={}", joinPoint.getSignature(), result);
      }

    ```

  - @Around 와 다르게 반환되는 객체를 변경할 수는 없다. 반환 객체를 변경하려면 @Around 를 사용해애한다. 참고로 반환 객체를 조작할 수 는 있다.
    - > 이거 앞선 코드에서 설명함

- @Around
  - 가능한 기능
    - 전달 값 변환: joinPoint.proceed(args[])
    - 반환 값 변환
    - 예외 변환
    - 트랜잭션 처럼 try ~ catch~ finally 모두 들어가는 구문 처리 가능
      - > 그냥 body에 try/catch 쓸수 있다는거 같음
    - proceed() 를 여러번 실행할 수도 있음(재시도)
      - > 그.. redismq에서 failover처리가 이거였나봄.

- 스프링은 5.2.7 버전부터 동일한 @Aspect 안에서 동일한 조인포인트의 우선순위를 정했다
  - pdf참조.
    - 실행 순서: @Around , @Before , @After , @AfterReturning , @AfterThrowing 👍
    - > 실행 순서라기 보단 advice가 걸린 순서로 보면 된다.
    - > 그럼 호출 순서는 위와 같고, 리턴순서는 반대라는게 쉽게 이해됨

- @Around 외에 다른 어드바이스가 존재하는 이유 👍
  - 개발자가 실수로 joinPoint.proceed() 를 빼먹을수 있기 때문에...
  - **좋은 설계는 제약이 있는 것이다**
  - 그리고 @Before를 이용하면 의도가 잘 드러난다
    - > 즉 target 호출전 무엇을 하는구나..를 알수 있음
    - > 단순 Around로 처리 해버리면, 뭔일을 하는지 다 봐야함( target호출 앞뒤로 뭘하는지...)

- Q/A
  - 실무에서는 위 어드바이스중 return 타입에 대한 부분은 잘 사용하지 않는다고 함
  - > ?
  
## 정리
