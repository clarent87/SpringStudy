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

- 500에러는 internal error
  - > 기본적으로 에러가 was까지 가면 이게 나옴 BasicErrorController json 응답.. 에 찍힘
  - > was가 500으로 처리하는것으로 설명됨.
  - 이걸 바꾸고 싶으면 HandlerExceptionResolver 메커니즘 이해 필요
- 400은 client 잘못.
- HandlerExceptionResolver 스프링이 제공
  
- pdf에 HandlerExceptionResolver 그림 중요.
  - 여기서 적용된 그림에서 당연히 인터셉터의 postHandler를 호출되지 않음.
  - 인터셉터의 postHAndler는 예외 발생되면 호출되지 않는다고 분명 앞에서 설명했음

- 사용법
  - HandlerExceptionResolver 를 구현하면 된다. code 참조
  - 예제에서는 `new ModleAndView()` 만들어서 반환하는데
    - 이러면 view 세팅이 안되서 view resovler는 동작을 안함
    - 일단 return은 정상객체니 정상 처리되는데
    - was에서는 sendError에 세팅된 에러를 보고 처리 시작
  - pdf에 아주잘 설명되어 있음

- **반환값에 따른 동작 방식**
  - 빈 ModelAndView : 뷰 렌더링 없이 정상 프름으로 서블릿이 리턴
    - > 예제에서는 500 error 나가는거 막으려고 여기서 request.sendError로 400에러로 치환해서 내보냄
  - ModelAndView 지정 : html 페이지 렌더링함. 즉 오류 페이지 렌더링 같은거 가능
    - > 앞선 강좌의 예외 flow ( 컨트롤러 -> 서블릿 -> was -> 다시 올라옴) 의 경우는 컨트롤러의 return이 view이지만 throw로 예러가 나갔을때 case
    - > 아마  ModelAndView 지정은, throw로 에러가 나가지 않으니 아마 렌더링된 뷰 만들어서 client에 전달하고 끝일듯...
    - > 단 request.sendError는 사용하지 않아야 겠지..HandlerExceptionResolver에서..
  - null
    - null 을 반환하면, 다음 ExceptionResolver 를 찾아서 실행한다. 만약 처리할 수 있는 ExceptionResolver 가 없으면 예외 처리가 안되고, 기존에 발생한 예외를 서블릿 밖으로 던진다.

- 그럼 이 HandlerExceptionResolver 를 어떻게 활용할수 있는가?
  - 예외 상태 코드 변환 : 예시 코드만든게 이내용임
  - 뷰 템플릿 처리 : ModelAndView 에 값을 채워서 예외에 따른 새로운 오류 화면 뷰 렌더링 해서 고객에게 제공
  - API 응답 처리 :
    - `response.getWriter().println("hello");` 처럼 HTTP 응답 바디에 직접 데이터를 넣어주는것도 가능하다.
    - 여기에 JSON 으로 응답하면 API 응답 처리를 할 수 있다.

- 등록
  - WebMvcConfigurer의 extendHandlerExceptionResolvers를 이용해야함
  - configureHandlerExceptionResolvers(..) 를 사용하면 스프링이 기본으로 등록하는 ExceptionResolver 가 제거되므로 주의 👍

- 특이
  - MyHandlerExceptionResolver 를 이용해서 IllegalArgumentException를 400에러로 sendError로 보냈는데
    - return json에서는 exception 필드는 여전히 IllegalArgumentException를 찍어주네..
  - 그리고 원래 앞쪽 강좌에서 컨트롤러 runtime error를 was까지 전파하면 디스패치서블릿에서도 로그 찍어 줬었는데.
    - > 예외핸들러로 일단 처리해 주니까 예외 로그가 안찍히는듯?

## HandlerExceptionResolver 활용 (12)

- API 예외에 HandlerExceptionResolver를 어떻게 활용 할수 있는지 나옴
- 핵심
  - 앞선 강좌에서는 예외가 was까지 갔다가 다시 컨트롤러로 올라가서 처리되었는데 이걸 해결한것

- 예외 로그
  - > 역시 서블릿 컨테이너에서 하나찍힌다고 언급함. ( 앞서 내가 로그 두개씩 나올때 작성한 내용과 일지..)
  - > 해당 로그는 ~main 이라는 문구가 있는듯

## 스프링이 제공하는 ExceptionResolver1

## 스프링이 제공하는 ExceptionResolver2

## @ExceptionHandler

## @ControllerAdvice

## 정리
