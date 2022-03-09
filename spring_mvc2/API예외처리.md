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

- **반환값에 따른 동작 방식** --> 이거 매우 중요!!
  - 빈 ModelAndView : 뷰 렌더링 없이 was까지 진행함. ( 보통은 예외를 수정해서 전달하는 목적인듯) --> 코드,PDF 참조
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
  - **앞선 강좌에서는 예외가 was까지 갔다가 다시 컨트롤러로 올라가서 처리되었는데 이걸 해결한것**

- 예외 로그
  - > 역시 서블릿 컨테이너에서 하나찍힌다고 언급함. ( 앞서 내가 로그 두개씩 나올때 작성한 내용과 일지..)
  - > 해당 로그는 ~main 이라는 문구가 있는듯

HandlerExceptionResolver 는 예외를 처리해주는거지 html/json case를 자동으로 해결해 주는것은아님!


## 스프링이 제공하는 ExceptionResolver1 (17)

- 스프링 부트가 기본으로 제공하는 ExceptionResolver
  - HandlerExceptionResolverComposite 에 다음 순서로 등록
  - 1. ExceptionHandlerExceptionResolver --> @ExceptionHandler 장에서 설명
  - 2. ResponseStatusExceptionResolver --> 여기서 강의함
  - 3. DefaultHandlerExceptionResolver 처리 우선 순위가 가장 낮다 --> 다음 절
  - > 순서가 중요. 1이 처리가 안되면 null을 반환하는데, 그러면 다음 resolver가 동작
  - > 1이 처리되면 다음은 넘어가지 않는듯. ( 그래서 순서가 중요!)

- ResponseStatusExceptionResolver
  - 다음 두가지를 처리
  - @ResponseStatus 가 달려있는 예외
  - ResponseStatusException 예외
  - > ResponseStatusExceptionResolver에 가보이 messageSource라는거 쓰고 있네 👍

- ResponseStatusExceptionResolver 원리
  - MyHandlerExceptionResolver와 동일.. 
  - 예외에서  @ResponseStatus 어노테이션이 있는지 보고, 있으면 status랑 메시지를 가져와서 sendError에 세팅
  - new ModelAndView()를 반환.
  - > null return이 아니기 때문에 다음 exceptionHandler로 넘어가지는 않음

- `@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")` 를 보면 messageSource에서 값을 찾는 기능도 제공
  - "error.bad" 가 해당 기능이다. 근데 messages.properties에서 해당 값을 못찾으면 그냥 error.bad가 출력됨
  - > BasicErrorController에 의해 출력되는 json format에 보면 status랑 message 찍히는 부분이 있음  
  - > 거기에 위 상태 코드랑, reason(메시지) 가 찍히는것  
    - ResponseStatusExceptionResolver의
    - applyStatusAndReason method 보면 앎. messageSoucre에서 getMessage 로 reason string을 key로 값 가져옴..
  - > 이때 당연히 server.error.include-message=always  세팅이 있어야 값이 찍힘
  - 당연 이 어노테이션은 내가 만든 코드에만 붙일수 있음. 시스템 코드나 라이브러리 코드 (-> 전부 빌드된 형태..)인 경우
    - 에는 붙일수 없어서.. ResponseStatusException 를 제공해준다. 
  - 그리고 특정조건에 따라 동적으로 값을 변하게 하기도 어려움.. 
    - 그럴때도 ResponseStatusException를 사용

- ResponseStatusException
  - `throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());`
  - 그냥 컨트롤러에서 위처럼 사용하면 된다.
  
> 보니까 @RestController 쓰고 status는 항상 ok찍어두고  + 나머지 예외는 위 처럼 처리하면될듯.??  
> 근데.. 중요한게 json format을 제어할수가 없네. api 마다 다를텐데.. 물론 BasicErrorController인경우도 상속해서 protected mehtod 오버라이드 하면되긴 하다고 했음..  
> 근데 api마다 예외 json 형태가 다르단건 말이 안되는거 같아서..(말이되는 케이스임. @ExceptionHandler  첫줄에 나옴)
> 위 아이디어로 개발하는것도 나쁘진 않아보임  
> 근데 비동기에서는 어찌 처리되려나.. 상관없나?

## 스프링이 제공하는 ExceptionResolver2 (20)

- DefaultHandlerExceptionResolver
  - 스프링 내부에서 발생하는 예외 해결 역할
  - 대표적으로 파라미터 바인딩 시점에 타입이 맞지 않으면 내부에서 TypeMismatchException 이 발생하는데, 
  - 이 경우 예외가 발생했기 때문에 그냥 두면 서블릿 컨테이너까지 오류가 올라가고, 결과적으로
    - > 서블릿 컨테이너? WAS 말하는 건가? 대충 그런 의미.
  - 500 오류가 발생한다
  - > DefaultHandlerExceptionResolver 가보면 spring 내부에서 터지는 예외들을 처리하는게 if/else로 잘 구분되어 있음
  - > 나중에 참조하면 좋음 ( do~ method에 있음)

앞서 예외가 was까지 갔다가 다시올라오는거 막으려고, ( 물론 sendError 그냥 보내서 다시 컨트롤러로 올라오게도 가능)
직접 ExceptionResolver를 만들어서 예외를 핸들링 해보려는 작업들을 했는데, 매우 불편   
보면 거의 서블릿 개발이지 spring 컨트롤러 개발이 아님.  
그래서 spring rest api 개발하듯 예외 처리할수 있게 해주는게 아래 나오는 내용

## @ExceptionHandler (22)

- 실무에서 api 예외처리는 전부 @ExceptionHandler로 진행

```java
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }
```

`@ExceptionHandler` 가 호출되는 시점은 당연히 ExceptionHandler 호출되는 시점임 (pdf의 앞쪽에 나왔던 그림 참조)  
ExceptionHandlerExceptionResolver가 우선순위가 높으니 이게 동작해서, 예외난 컨트롤러 class에 `@ExceptionHandler`가 있는지 보고  
그거 호출해줌 (ExceptionHandlerExceptionResolver 코드는 매우 복잡하다함)   
> 코드 보면 핸들러 정보 다넘어가고 있어서 @ExceptionHandler 찾는건 어렵지 않다고 함.  
그리고 `@ExceptionHandler` 붙은 method에 @ResponseBody같은 것도 적용 가능
  
이걸로 처리하면 당연히 was에서 다시 컨트롤러로 예외가 올라가지 않음 ( 정상 흐름으로 처리하니까.. 이부분은 코드 참조)  

- ApiExceptionV2Controller에 다양한 예제 작성함
- pdf의 "@ExceptionHandler 예외 처리 방법" 절 부터 정리된 내용이 나오는데 매우 중요

- 아래 처럼 한번에 여러 exception type 처리 가능
```java
@ExceptionHandler({AException.class, BException.class})
public String ex(Exception e) { // AException, BException 의 부모 class 넣어줘야함.. 당연..그래야 두 type을 받을수 있지..
 log.info("exception e", e);
}

```
  
예시에서 만든 @ExceptionHandler method로는 param으로 exception만 받았는데. 사실 다양한 파라메터를 받을수 있고, 응답으로도
다양한 type 쓸수 있다. 마치 controller(핸들러) 만들던거 처럼.. 
> 여기서 controller는 controller class 말하는거 아님. method를 지칭. 원래 그걸 지칭하는거..  
> pdf에 지원하는 파라메터, 응답link있음
  
- ExceptionHandler에서 `@responseBody` 없이 string 반환하면 viewResolver 동작
  - > `@responseBody` 있으면. 그냥 http body에 string찍히는 거였나.. 그럼 json은 object일때만 치환해주는거 였고..
  - > 원리는 objectMapper.

- ExceptionHandler에 @ResponseStatus를 붙여서 상태코드 줄수도 있지만. 
  - 여전히 ResponseEntity를 이용해서 동적으로 상태코드 처리도 가능 
  - > ExceptionHandler에 @ResponseStatus를 붙이는 것만으로도 대다수 처리 가능할거 같은데.. 
  - > 그래도 이게 필요한 case들이 있긴 한듯
  
아직 단점이 있음 ExceptionHandler는 정의한 controller에서 밖에 못씀.. 즉 딴데서도 쓰려면 복붙해야함..
그래서! ControllerAdvice가 나옴


## @ControllerAdvice (29)

- @ExceptionHandler 를 사용해서 예외를 깔끔하게 처리할 수 있게 되었지만, 
- 정상 코드와 예외 처리 코드가 하나의 컨트롤러에 섞여 있다. 
- @ControllerAdvice 또는 @RestControllerAdvice 를 사용하면둘을 분리할 수 있다

- `@RestControllerAdvice`
  - 앞서 작업한 exceptionHandler를 위 어노테이션 붙은 class에 모아둘수 있다
    - 이러면 모든 controller에 exceptionhandler가 다걸림 (대상 지정 안했기 때문)
  - 즉 마치 controller용 aop같다.
  - RestControllerAdvice나 ControllerAdvice나 같은것. RestControllerAdvice는 responseBody가 붙은게 차이임
  - 원래 대상 지정해서 쓸수 있는데, 대상 지정 안하면 모든 컨트롤러에 다걸림

AOP랑 비슷.. Advice란 이름도 AOP에서 온것  

- 대상 컨트롤러 지정
  - Pdf에 잘나옴 3종류 있음. 
  - 보통 `@ControllerAdvice("org.example.controllers")` 처럼 package 정도는 지정해줌
    - 즉 해당 패키지 아래에 다걸림

  
실무에서는 exceptionHandler + advice 조합해서 잘 쓰는게 중요.  유용하다

## 질문

- @ResponseStatus는 내부적으로 response.sendError(statusCode, resolvedReason); 를 통해 response 내부에 오류가 발생했었음을 상태로 저장하게 됩니다.(이전 강의에서 알려주셨던 내용)
이후 이를 WAS가 인지하게 되어 기본 에러 페이지인 "/error"로 재요청을 보내야 하는것 아닌가요?
  - @ResponseStatus를 예외에서 사용할 때는 말씀드린 내용처럼 동작하고, 컨트롤러나 @ExceptionHandler에서 사용할 때는 단순히 상태코드를 변경하는 방식으로 동작합니다.


## 정리

- ExceptionResolver (9p)
  - 이거 호출하는 코드는 디스패쳐서블릿에 있다고 함
    - 빈 ModelAndView, ModelAndView 지정, null 에 따른 동작이 코드에 있음