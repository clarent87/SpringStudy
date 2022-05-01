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

## execution - 1

## execution - 2

## within

## args

## @target, @within

## @annotation, @args

## bean

## 매개변수 전달

## this, target

## 정리
