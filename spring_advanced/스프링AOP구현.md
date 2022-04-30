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

- 포인트
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
  - 스프링 빈으로 등록하는 방법
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
    - > 어드바이저가 여러개 생성되는게 맞을거 같긴함
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

## 스프링 AOP 구현 6 - 어드바이스 종류

## 정리
