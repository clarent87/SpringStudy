# API 예외 처리

## 시작

- 기존 exception project에서 작업함. 앞선 강좌처럼 서블릿 예외 처리부터 시작
- 단순히 restapi에서 에러 발생시키면, 앞서 만들어둔 errorController에 의해 사용자에게 html이 반환됨
  - 따라서 에러컨트롤러도 json버전을 만들어야함
  
여기서는 앞선 서블릿 오류페이지 등록 메커니즘으로 처리해봄.. ( 코드 확인 해봐라.)

> 근데 비동기에서도 앞선 필터/인터셉터의 동작이 보장될까??? 즉 에러 발생 -> was -> 다시 에러컨트롤러 -> 반환.. 이구조.. 

## 스프링 부트 기본 오류 처리 (5)

여기서는 앞서 배운 스프링 부트 BasicErrorController를 이용해봄  
이미 json으로 return하는 컨트롤러가 구현되어 있음 ( request의 accept에 따라 html인경우 html 버전이 동작, 아닌경우는 json 버전이 동작)
  
앞선 강좌의 스프링 부트 BasicErrorController를 이용했던거 처럼. 아래 properties 를 제공

```
server.error.include-binding-errors=always 
server.error.include-exception=true 
server.error.include-message=always 
server.error.include-stacktrace=always
```
  
- BasicErrorController(AbstractErrorController) 의 getErrorAttributes를 override하면 반환되는 json 형태를 변경할수도 있다. 
  - > 안쓴다함. 더 좋은거 있어서. 
  - > 더 좋은거 -> @ExceptionHandler  이게 끝판왕. 이걸 이해하기 위해서 아래 강좌를 들어야함

## HandlerExceptionResolver 시작 (7)

## HandlerExceptionResolver 활용

## 스프링이 제공하는 ExceptionResolver1

## 스프링이 제공하는 ExceptionResolver2

## @ExceptionHandler

## @ControllerAdvice

## 정리
