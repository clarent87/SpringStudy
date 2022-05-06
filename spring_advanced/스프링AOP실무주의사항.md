# 스프링 AOP - 실무 주의 사항

아래 두가지 문제에 대해서 다루는 강좌

- 프록시와 내부 호출
- 프록시 기술과 한계

## 프록시와 내부 호출 - 문제

실무에서 반드시 한번은 겪는 문제라고 한다.  

- 개요
  - 여지껏 나온 개념이긴 하다
  - 프록시가 적용되려면 항상 프록시 객체를 통해 method를 호출해야함
  - 즉, target 객체를 이용해서 method를 호출하면 당연히 advice는 적용이 안된채로 호출됨.
  - CallServiceV0Test class 및 기타 클래스를 통해 문제를 보임

- 포인트
  - 아래 external, internal 모두 advisor가 걸린 상태인데, external 호출시, method내부에서 호출하는 internal은 this.internal
  - 즉, proxy의 target 객체라서 advisor가 걸린게 호출되는게 아님.

  ```java
    public void external() {
        log.info("call external");
        internal(); //내부 메서드 호출(this.internal())
                    // java에서 this.internal()로 호출해 준다. -> 바이트코드상 당연한 얘기
    }

    public void internal() {
        log.info("call internal");
    }
  ```

요거 면접때 물어본다함
  
- 프록시 방식의 AOP 한계 👍
  - 스프링은 프록시 방식의 AOP를 사용한다.
  - 프록시 방식의 AOP는 메서드 내부 호출에 프록시를 적용할 수 없다.
  - 지금부터 이 문제를 해결하는 방법을 하나씩 알아보자

- 참고(중요) 👍
  - 실제 코드에 AOP를 직접 적용하는 AspectJ를 사용하면 이런 문제가 발생하지 않는다.
  - 프록시를 통하는 것이 아니라 해당 코드에 직접 AOP 적용 코드가 붙어 있기 때문에 내부 호출과 무관하게 AOP를 적용할 수 있다.
  - 하지만 로드 타임 위빙 등을 사용해야 하는데, 설정이 복잡하고 JVM 옵션을 주어야 하는 부담이 있다.
  - 그리고 지금부터 설명할 프록시 방식의 AOP에서 내**부 호출에 대응할 수 있는 대안**들도 있다.
    -  이런 이유로 AspectJ를 직접 사용하는 방법은 실무에서는 거의 사용하지 않는다.
    - 스프링 애플리케이션과 함께 직접 AspectJ 사용하는 방법은 스프링 공식 메뉴얼을 참고하자.
    - <https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-using-aspectj>

## 프록시와 내부 호출 - 대안1 자기 자신 주입

## 프록시와 내부 호출 - 대안2 지연 조회

## 프록시와 내부 호출 - 대안3 구조 변경

## 프록시 기술과 한계 - 타입 캐스팅

## 프록시 기술과 한계 - 의존관계 주입

## 프록시 기술과 한계 - CGLIB

## 프록시 기술과 한계 - 스프링의 해결책

## 정리
