# 스프링 AOP - 실전 예제

## 예제 만들기

- exam 패키지에 예제 만듬. src/test 모두에 만듬
- 별거 없음

앞으로 로그 출력 AOP, 재시도 AOP를 만들 예정.
이런식으로 AOP를 이용하는거 자주 하나봄.  
  
재시도의 경우 간헐적으로 서버가 내려갔다 올라오는 곳에 요청 할때, 그리고 단순 select면 재시도 해도 문제 없음  
강의에서도 이런식으로 언급함

## 로그 출력 AOP (3)

- 개요
  - 배운걸 이용해서 로그 출력해주는 aop만듬

@Trace 가 메서드에 붙어 있으면 호출 정보가 출력되는 편리한 기능을 만듬

## 재시도 AOP (6)

- 개요
  - @Retry 애노테이션이 있으면 예외가 발생했을 때 다시 시도해서 문제를 복구한다.
  - 재시도 횟수도 세팅가능하게 예제를 준비

- 포인트
  - RetryAspect 예시를 만들었는데, logic이 돌아가는게 특이

  ```java
  
    for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
        try {
            log.info("[retry] try count={}/{}", retryCount, maxRetry);
            return joinPoint.proceed(); // 정상 종료되면 함수를 나가는데, 혹시 에러가 발생하면 for문을 계속 돌게 된다. (retrycount까지)
        } catch (Exception e) {
            exceptionHolder = e;
        }
    }
  ```

- 포인트2
  - `public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry)`
    - > 여기서 Retry는 class가 아니라 어노테이션이라는거 주의깊게 봐야함

- 중요 사항! 👍
  - 예시에서는 catch에서 Exception만 잡음. Throwable은 Exception보다 상위인데 이건 java memory가 없거나 등등,
  - 진짜 개발자가 어떻게 할수없는 에러이다.
  - 따라서 Exception까지만 보통 catch함.

- 중요 포인트
  - retry 로직을 만들땐 반드시 retry 횟수가 세팅되어야한다.
  - 만약 외부 service를 호출하는것에 retry를 하는데 반복횟수가 세팅되어 있지 않으면, 셀프 ddos가 되는거.. ㄷㄷ

- 참고
  - 스프링이 제공하는 @Transactional 은 가장 대표적인 AOP이다.

## 정리

- 실무에서 AOP 관련 기능 구현 할때, 공통적으로 필요한거 만들어 쓰면 된다. 
  - ex
    - 여기에서는 Trace 로 단순 로그만 남겼는데.
    - 1. 옵션을 주어서 수행시간이 특정시간 이상되었을때, 또는 예외가 터졌을때 로그를 남기는 형태
  - > 사실 스프링도 이런 AOP 많이 만들어 놨다고 함
  - > @Async 얘기도 나옴. 이것도 AOP의 예시