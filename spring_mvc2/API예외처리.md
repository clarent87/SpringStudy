# API 예외 처리

## 시작

- 기존 exception project에서 작업함. 앞선 강좌처럼 서블릿 예외 처리부터 시작
- 단순히 restapi에서 에러 발생시키면, 앞서 만들어둔 errorController에 의해 사용자에게 html이 반환됨
  - 따라서 에러컨트롤러도 json버전을 만들어야함
  
여기서는 앞선 서블릿 오류페이지 등록 메커니즘으로 처리해봄.. ( 코드 확인 해봐라.)

> 근데 비동기에서도 앞선 필터/인터셉터의 동작이 보장될까??? 즉 에러 발생 -> was -> 다시 에러컨트롤러 -> 반환.. 이구조.. 

## 스프링 부트 기본 오류 처리 (5)

여기서는 앞서 배운 스프링 부트 BasicErrorController를 이용해봄

## HandlerExceptionResolver 시작

## HandlerExceptionResolver 활용

## 스프링이 제공하는 ExceprionResolver1

## 스프링이 제공하는 ExceprionResolver2

## @ExceptionHandler

## @ControllerAdvice

## 정리
