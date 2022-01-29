# 스피링 MVC 1

## 웹 애플리케이션 이해

### 웹 서버, 웹 애플리케이션 서버

원래 알던 개념과 같다.  
보통 WAS도 웹서버의 역할을 하니까, 서비스 구성시 WAS + DB만 있어도 되긴하는데  
이러면 WAS에 부하가 많이 걸리고, 로직에 오류가 나면 서버가 내려가는데, 이떄 정적인 resource 조차도 client에게 서빙하지 못하는 단점이 있음  
그래서 보통 Web Server + WAS + DB 형태로 서비스를 구성함

WAS랑 web 서버 경계가 현재는 약간 모호할수 있는데, java 진영에선 서블릿을 지원하는 서버면 WAS로 보기도 함.

### 서블릿(15)

pdf참조
  
동시 요청을 위한 멀티 쓰레드 지원

### 동시 요청 - 멀티 쓰레드(22)

WAS에서 멀티 쓰레드 지원하는데, 쓰레드 풀로 지원함.  
요청마다 쓰레드 생성하는것은 오버헤드가 너무 크고, 무한대로 쓰레드를 생성해 줄수도 없기때문  
톰갯의 쓰레드 풀은 최대 200이 기본 설정 (변경가능)  

- 🌟실무 팁. 성능 튜닝 (39)

### HTML, HTTP API, CSR, SSR

대강 다 아는 내용  

- 백엔드 개발자가 어디까지 알아야 하는가?
  - jsp 는 사장되는 기술이니 타입리프를 스터디 하는것을 추천 (하루 이틀이면 master한다함) -> ssr
  - ssr은 필수로 알아야함.
  - ssr은 화면이 정적이고, 복잡하지 않을때 사용

### 자바 백엔드 웹 기술 역사

- spring mvc
- spring webflux
  - 아직은 RDB 지원 부족. 그래서 RDB쓰는경우는 이거 쓰지 않는게 좋다. (redis랑 mongoDB는 괜찮다. )

- 뷰 템플릿
  - 백엔드에서 html을 동적으로 생성하는 기능
  - ex: jsp, 타임리프 등.

## 서블릿 (22)

### 프로젝트 생성

- 스프링 부트를 쓰지는 않지만, 프로젝트 만들기 편리해서 스프링 부트로 진행
- jsp를 돌리기 위해 WAR를 선택해서 프로젝트 만듬 (보통의 경우 jar를 선택)
  - WAR는 톰캣이 없는데, 넣을수도 있다.
    - spring web 디펜던시 주면 들어가는듯.
  - 보통 WAR는 톰캣을 따로 운영할때 씀

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
    - 이거 곂지면 안됨 다른것들이랑.

- 간단한 welcomepage추가
  - 강의 내용 정리용?
  - > index.html은 localhost:8080 접속시 뜨는데,, 위치는 대충 만들어도 되는건가? 알아서 찾아서 진행하나?
  - > 강의에서는 webapp이라는 폴더를 만들어서 진행

### HttpServletRequest - 개요 (13)

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


