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

## 스프링 AOP 구현 4 - 포인트컷 참조

## 스프링 AOP 구현 5 - 어드바이스 순서

## 스프링 AOP 구현 6 - 어드바이스 종류

## 정리
