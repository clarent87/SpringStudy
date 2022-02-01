# 스피링 MVC 1

## 웹 애플리케이션 이해

### 웹 서버, 웹 애플리케이션 서버

원래 알던 개념과 같다.  
보통 WAS도 웹서버의 역할을 하니까, 서비스 구성시 WAS + DB만 있어도 되긴하는데  
이러면 WAS에 부하가 많이 걸리고, 로직에 오류가 나면 서버가 내려가는데, 이떄 정적인 resource 조차도 client에게 서빙하지 못하는 단점이 있음  
그래서 보통 Web Server + WAS + DB 형태로 서비스를 구성함

WAS랑 web 서버 경계가 현재는 약간 모호할수 있는데, java 진영에선 서블릿을 지원하는 서버면 WAS로 보기도 함.

> 스프링부트로 web server - was 세팅을 구분해서 할수 있나?즉 자바로직이 죽었을떄
> web server는 유지 되게?

### 서블릿(15)

pdf참조
  
- 서블릿 지원 was는 동시 요청을 위한 멀티 쓰레드 지원

- 서블릿 컨테이너가 서블릿은 싱글톤으로 관리
  - **bean 처럼 공유변수(멤버변수)관리에 매우 신경써야햠. (공유변수 쓴다면.)**

### 동시 요청 - 멀티 쓰레드(22)

WAS에서 멀티 쓰레드 지원하는데, 쓰레드 풀로 지원함.  
요청마다 쓰레드 생성하는것은 오버헤드가 너무 크고, 무한대로 쓰레드를 생성해 줄수도 없기때문  
톰갯의 쓰레드 풀은 최대 200이 기본 설정 (변경가능)  

- 🌟실무 팁. 성능 튜닝 (39)
  - 쓰레드 풀의 적정 숫자 맞추는게 중요. cpu는 한 50퍼센트는 써야
  - cpu,메모리 리소스 임계값을 넘어가면 서버가 다운됨
  - **실 서비스전 성능테스트를 진행하고, 성능 튜닝 해야한다.**
  - 툴 : 아파치 ab, 제이미터, nGrinder(추천)

### HTML, HTTP API, CSR, SSR

대강 다 아는 내용  

- 백엔드 개발자가 어디까지 알아야 하는가?
  - jsp 는 사장되는 기술이니 **타입리프**를 스터디 하는것을 추천 (하루 이틀이면 master한다함) -> ssr
  - ssr은 필수로 알아야함.
  - ssr은 화면이 정적이고, 복잡하지 않을때 사용

### 자바 백엔드 웹 기술 역사

- spring mvc
- spring webflux
  - 비동기 넌블럭킹
  - 아직은 RDB 지원 부족. 그래서 RDB쓰는경우는 이거 쓰지 않는게 좋다. (redis랑 mongoDB는 괜찮다. )
  - 서플릿 기술이 아니고, netty로 구현이 되어 있음
  - 아직은 실무에서 잘 안씀 ( 성능이 매우 중요하고, 뒤에 여러개 api호출하는 등 매우 복잡할때 쓰면좋다.)
    - > 실무에서 사용 가능하긴 하다.

- 뷰 템플릿
  - 백엔드에서 html을 동적으로 생성하는 기능
  - ex: jsp, 타임리프 등.

## 서블릿 (22)

### 프로젝트 생성

- 서블릿 이용하면 스프링 부트를 안써도되는데, 프로젝트 만들기 편리해서 스프링 부트로 진행
- jsp를 돌리기 위해 WAR를 선택해서 프로젝트 만듬 (보통의 경우 jar를 선택)
  - WAR는 톰캣이 없는데, 넣을수도 있다.
    - spring web 디펜던시 주면 들어가는듯.
  - 보통 WAR는 톰캣을 따로 운영할때 씀

- annotation process는 enable 해줘야함
- postman 설치
  - API 테스트에 편리

### hello 서블릿 (6)

- 단축키
  - ctrl+o : override할 method 가져옴

- 서블릿 쓰면 WAS에서 "HttpServletRequest request, HttpServletResponse response" 전달해줌. 개발자는 이것만 다루면됨 ( http parsing은 was가 해준것)
- `response.setCharacterEncoding("utf-8");`
  - 요즘은 웬만하면 utf-8로 문자열 인코딩 한다.

- 크롬 개발자 도구로 req, res 내용 어느정도 확인 가능
- `@WebServle`
  - 서블릿 이름이랑 url 매핑을 줌.
    - **이거 곂지면 안됨** 다른것들이랑.

- 간단한 welcomepage추가
  - 강의 내용 정리용?
  - > index.html은 localhost:8080 접속시 뜨는데,, 위치는 대충 만들어도 되는건가? 알아서 찾아서 진행하나?
  - > 강의에서는 webapp이라는 폴더를 만들어서 진행

### HttpServletRequest - 개요 (13)

- http format은 아래 처럼 구성됨
  - start line
  - header
  - body

HttpServletRequest 는 임시 저장 기능 세션 관리 기능을 추가적으로 제공
> 임시 저장 기능이거 프로젝트에서 본듯
  
책 추천) http 완전 정복 가이드
  
### HttpServletRequest - 기본 사용법 (14)

여기서는 servlet의 기능으로 http req의 파싱내용 보여주는것을 진행함.

- HttpServlet의 service override할땐 protected인걸 선택해야함(public말고.)
- `logging.level.org.apache.coyote.http11=debug` 를 세팅해서.. 모든 rq의 정보가 기본적으로 출력됨
  - 이거 이전 강좌에서 실제 환경에서는 쓰지 않는게 좋다고 했었음 ( 성능이슈라고 한듯?)

- `getContentType`
  - get 방식으로 rq가 오면 이건 null
  - 왜냐면 content type은 body가 있을때만 유의미

### Http 요청 데이터  - 개요 (20)

http 요청 방식 설명. get, post, HTTP message body에 데이터를 직접 담아서 요청-

- `content-type: application/x-www-form-urlencoded`
  - post body의 message형식을 나타내는건데 x-www-form-urlencoded 라고 하면, 그 형식이 GET 방식의 쿼리 string과 동일하다.

- HTTP message body에 데이터를 직접 담아서 요청
  - > post인데 content-type을  JSON, XML, TEXT를 이용할는 것을 말하는듯

### HTTP 요청 데이터 - GET 쿼리 파라미터 (21)

- 단축키
  - `service`
    - 앞에선 ctrl+o로 override할 method 찾았는데, 그냥 service 치고 선택해도 됨.

- `username=hello&age=20&username=hello2`
  - 위 처럼 get 쿼리 스트링에 username param에 값을 여러번 넣어서 전달 가능 (즉 username param에 값이 여러개 들어가서 전달되는것..
    - >물론 서버로 퀴리 스트링은 위와 똑같이 들어오겠지만, 서블릿에서 username은 복수의 값을 가지는것으로 처리해준다는거 같음
  - 이땐 기본적으로 getParameter 시 내부우선순위에 의해 먼저 잡히는게 나옴.
  - 또는 getParameterValues 로 복수의 값을 확인 가능
    - 중복일 때 request.getParameter() 를 사용하면 request.getParameterValues() 의첫 번째 값을 반환한다

- 단축키
  - `iter` 치면 for loop 나옴

### HTTP 요청 데이터 POST HTML Form (25)

- form 이용한 request는 content-type이 있는게 특징
- project의 webapp 폴더 아래 tree 구조에 따라 url이 매핑되는 부분이 신기함 ( 이거 가이드 있나? )
  - ex: `http://localhost:8080/basic/hello-form.html`  

- 현재까지 강의 요점 간단요약
  - javx의 서블릿의 webSerlet 을 이용해서 get req 처리하는거 까지 함.
  - 그리고 신기하게도 HttpServlet을 상속한 class를 만들고 거기에 service를 override함. url는 method가 아닌 class에 붙여준다.
    - > 아마 template method 패턴이 아닌가 싶음.

- 폴더 구성을 보면 webapp이라는 폴더(이건 원래 있던건가?)에 basic 폴더를 만들고 그 안에 `hello-form.html`을 만들었다.
  - 이경우 접근은 `http://localhost:8080/basic/hello-form.html` 형태로 폴더 path를 명시 해준다.

- 본 장에서는 post message를 get method로 전달했는데, 아무 문제가 없었다.
  - get의 쿼리나 post의 application/x-www-form-urlencoded 인코딩 방식은 형태가 동일
  - 그래서 `HttpServletRequest request`의 `request.getParameter`동작시 아무 문제가 없음.

- postman test로 진행해봄

### HTTP 요청 데이터 - api 메시지 바디 - 단순 텍스트 (27)

- 현재까지 강의 요점 간단요약
  - post나 get이나 httpServletRequest를 이용하는 것으로 보인다.

- 현재 섹션에서는 content/type text/string으로 message 주고 받는거 진행함
  - > message전달은 postman으로..

### Http 요청 데이터 - API 메시지 바디 -json (28)

- 보통은 json을 받아서 객체로 바꾸어서 쓴다.

참고. JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환
라이브러리를 추가해서 사용해야 한다. 스프링 부트로 Spring MVC를 선택하면 기본으로 Jackson
라이브러리( ObjectMapper )를 함께 제공한다

- 코드로 내용은 파악하도록하고, 주석 달아둠.

여기 까지 3가지 요청을 처리하는 법을 알아보았는데, 이 정도만 알면 된다고 한다. 이 이상 벗어나지 않는다고 함.

> jackson 이용할때 param이 매핑되지 않는 case 등에 대한 annotation 세팅이 있었다.

### HttpServletResponse - 기본사용법 (32)

헤더 세팅을 직접 진행해보고, 그다음 편의 메소드로 조금더 쉽게 하는 방법 진행  

- pragma 세팅은 내 책 내용이랑은 쫌 다른듯.. 가능하면 http 강의 구매후 들어보는것도 좋겠음

### HTTP 응답 데이터 - 단순 텍스터,HTML (34)

- 1:56
  - 단축키 나오는데 그냥 생략함. 창 크기 조절및 창 옮겨다니기

### Http 응답 데이터 - API JSON (36)

application/json 은 스펙상 utf-8 형식을 사용하도록 정의되어 있다.  
그래서 스펙에서 charset=utf-8과 같은 추가 파라미터를 지원하지 않는다.
따라서 application/json 이라고만 사용해야지 `application/json;charset=utf-8` 이라고 전달하는 것은 의미 없는 파라미터를 추가한 것이 된다.  
근데  response.getWriter()를 사용하면 추가 파라미터를 자동으로 추가해버린다.
이때는 response.getOutputStream()으로 출력하면 그런 문제가 없다.

### 정리

- html 스펙상 form data를 body로 전송할때 는 POST 만 가능
  - > spring 에서는 put으로 body를 전송? 해도 받아주나봄. 실제로는 post로 가고 hidden 필드에 put이라고 쓴다는데..
  - > 이게 resttemplate으로 보낼떄 얘기인지? 아니면 받아줄때 얘기인지 잘 모르겠음

## 3. 서블릿, jsp, mvc 패턴

### 회원관리 웹 애플리케이션 요구사항

### 서블릿으로 회원 관리 웹 애플리케이션 만들기 (42)

- 문제점이 서블릿 만으로 http page를 response해주기가 어렵다.
  - java코드로 일일이 http tag를 string으로 만들어서 page를 작성해야함

- index파일 접근시 304로 톰캣이 응답을 주는데, 이건 browser에서 If-Modified-Since 헤더를 쓰기 때문
  - <https://itstory.tk/entry/Spring-MVC-LastModified-IfModifiedSince>-캐시-설정

### JSP로 회원 관리 웹 애플리케이션 만들기 (50)

- 아직 jsp 파일안에 비지니스 로직 과 뷰로직이 짬뽕되어 있음. 프로젝트가 커지면 유지 보수가 안된다.
  - 따라서 jsp + servlet을 이용해서 mvc 패턴을 적용하는 강의가 다음 주제이다. ( 비지니스 및 뷰를 분할)

### MVC 패턴 - 개요 (55)

- mvc 소개가 일반 MVC랑은 약간 다르다 Model 개념이 다름
  - 정석적인 mvc 패턴은 df에 있는게 맞음 ( model에 비지니스 로직이 있는..)

### MVC 패턴 - 적용 (58)

- 서블릿을 컨트롤러로 사용하고, jsp를 뷰로 활용
  - 컨트롤러에서는 비지니스 로직을 service로 나누어서 호출한다.
    - > 전통적인 mvc를 보년 Model에 비지니스 로직이 있음. 컨트롤러에서는 model을 호출  
    - > 따라서 여기서 말하는 servcie는 Model이라고 볼수가 있음. 즉 전통적인 Model은 Service + (data)Dao 같은 개념이라고 보여짐

- `/WEB-INF`
  - 이 경로안에 JSP가 있으면 외부에서 직접 JSP를 호출할 수 없다. 우리가 기대하는 것은 항상 컨트롤러를 통해서 JSP를 호출하는 것이다.
  - 이거 WAS 서버 룰임. 즉 static resource요청하듯 client가 요청할수 없다는것.

### MVC 패턴 - 한계 (65)

- jsp를 통해서 html page를 반환하기 때문에 HttpServletResponse를 쓰지 않고 있음

- 정리하면 공통 처리가 어렵다는 문제가 있다.

이 문제를 해결하려면 컨트롤러 호출 전에 먼저 공통 기능을 처리해야 한다.
소위 수문장 역할을 하는 기능이 필요하다.
**프론트 컨트롤러(Front Controller) 패턴**을 도입하면 이런 문제를 깔끔하게 해결할 수 있다. (입구를 하나로!)
**스프링 MVC의 핵심도 바로 이 프론트 컨트롤러에 있다.**
  
보통 MVC 프레임워크들은 이 프론트 컨트롤러 패턴을 구현한것이다.
  
> 프론트 컨트롤러는 spring에 있는 filter 랑은 다르다
  
### 정리

## 4. MVC 프레임 워크 만들기

### 프론트 컨트롤러 패턴 소개 (67)

- 프론트 컨트롤러 "서블릿" 이 모든 요청을 받아서 공통 처리후, 다른 컨트롤러를 호출해줌
  - > 이게 핵심
  - 스프링 웹 MVC의 DispatcherServlet이 FrontController 패턴으로 구현되어 있음

### 프론트 컨틀롤러 도입 - v1 (68)

### View 분리 - v2 (74)

- MyView를 통해서 jsp forwarding 진행
  - > 이경우는 jsp를 통해서 html를 반환하는 case인건데.. 단순 json return일땐? 어떻게 되는 걸까?
- request, response 처리는 현재는 거의 jsp에서 만 처리한다.  ( 아직 save에서 쓰기는 함)
  - > 그래서 이부분이 필요없어서 개선하려고 함 다음 장에서..

### Model 추가 - v3 (80)

- jsp render를 위한 MyView 대신 ModelView라는것을 생성함
  - 차이점은 jsp로 원래 request, response를 직접 넘기는거 대신, 필요한 data만 넘김

- 뷰 리졸버
  - jsp의 디렉토리가 변경되었을때, 개별 controller를 손보지 않아도 된다. 

### 단순하고 실용적인 컨트롤러 - v4 (89)

### 유연한 컨트롤러1 - v5

### 유연한 컨트롤러2 - v5

### 정리
