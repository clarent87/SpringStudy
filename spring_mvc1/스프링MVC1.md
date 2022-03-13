# 스프링 MVC 1

- [스프링 MVC 1](#스프링-mvc-1)
  - [웹 애플리케이션 이해](#웹-애플리케이션-이해)
    - [웹 서버, 웹 애플리케이션 서버](#웹-서버-웹-애플리케이션-서버)
    - [서블릿(15)](#서블릿15)
    - [동시 요청 - 멀티 쓰레드(22)](#동시-요청---멀티-쓰레드22)
    - [HTML, HTTP API, CSR, SSR](#html-http-api-csr-ssr)
    - [자바 백엔드 웹 기술 역사](#자바-백엔드-웹-기술-역사)
  - [서블릿 (22)](#서블릿-22)
    - [프로젝트 생성](#프로젝트-생성)
    - [hello 서블릿 (6)](#hello-서블릿-6)
    - [HttpServletRequest - 개요 (13)](#httpservletrequest---개요-13)
    - [HttpServletRequest - 기본 사용법 (14)](#httpservletrequest---기본-사용법-14)
    - [Http 요청 데이터  - 개요 (20)](#http-요청-데이터----개요-20)
    - [HTTP 요청 데이터 - GET 쿼리 파라미터 (21)](#http-요청-데이터---get-쿼리-파라미터-21)
    - [HTTP 요청 데이터 POST HTML Form (25)](#http-요청-데이터-post-html-form-25)
    - [HTTP 요청 데이터 - api 메시지 바디 - 단순 텍스트 (27)](#http-요청-데이터---api-메시지-바디---단순-텍스트-27)
    - [Http 요청 데이터 - API 메시지 바디 -json (28)](#http-요청-데이터---api-메시지-바디--json-28)
    - [HttpServletResponse - 기본사용법 (32)](#httpservletresponse---기본사용법-32)
    - [HTTP 응답 데이터 - 단순 텍스터,HTML (34)](#http-응답-데이터---단순-텍스터html-34)
    - [Http 응답 데이터 - API JSON (36)](#http-응답-데이터---api-json-36)
    - [정리](#정리)
  - [3. 서블릿, jsp, mvc 패턴](#3-서블릿-jsp-mvc-패턴)
    - [회원관리 웹 애플리케이션 요구사항](#회원관리-웹-애플리케이션-요구사항)
    - [서블릿으로 회원 관리 웹 애플리케이션 만들기 (42)](#서블릿으로-회원-관리-웹-애플리케이션-만들기-42)
    - [JSP로 회원 관리 웹 애플리케이션 만들기 (50)](#jsp로-회원-관리-웹-애플리케이션-만들기-50)
    - [MVC 패턴 - 개요 (55)](#mvc-패턴---개요-55)
    - [MVC 패턴 - 적용 (58)](#mvc-패턴---적용-58)
    - [MVC 패턴 - 한계 (65)](#mvc-패턴---한계-65)
    - [정리](#정리-1)
  - [4. MVC 프레임 워크 만들기](#4-mvc-프레임-워크-만들기)
    - [프론트 컨트롤러 패턴 소개 (67)](#프론트-컨트롤러-패턴-소개-67)
    - [프론트 컨틀롤러 도입 - v1 (68)](#프론트-컨틀롤러-도입---v1-68)
    - [View 분리 - v2 (74)](#view-분리---v2-74)
    - [Model 추가 - v3 (80)](#model-추가---v3-80)
    - [단순하고 실용적인 컨트롤러 - v4 (89)](#단순하고-실용적인-컨트롤러---v4-89)
    - [유연한 컨트롤러1 - v5 (95)](#유연한-컨트롤러1---v5-95)
    - [유연한 컨트롤러2 - v5 (103)](#유연한-컨트롤러2---v5-103)
    - [정리 (106)](#정리-106)
  - [5. 스프링 MVC - 구조 이해](#5-스프링-mvc---구조-이해)
    - [스프링 MVC 전체 구조 (107)](#스프링-mvc-전체-구조-107)
    - [핸들러 매핑과 핸들러 어댑터 (112)](#핸들러-매핑과-핸들러-어댑터-112)
    - [뷰 리졸버 (117)](#뷰-리졸버-117)
    - [스프링 MVC - 시작하기 (120)](#스프링-mvc---시작하기-120)
    - [스프링 MVC - 컨트롤러 통합 (125)](#스프링-mvc---컨트롤러-통합-125)
    - [스프링 MVC - 실용적인 방식 (127)](#스프링-mvc---실용적인-방식-127)
    - [정리(스프링MVC)](#정리스프링mvc)
  - [6. 스프링 MVC - 기본 기능](#6-스프링-mvc---기본-기능)
    - [프로젝트 생성 (130)](#프로젝트-생성-130)
    - [로깅 간단히 알아보기 (135)](#로깅-간단히-알아보기-135)
    - [요청 매핑 (138)](#요청-매핑-138)
    - [요청 매핑 - API예시 (144)](#요청-매핑---api예시-144)
    - [HTTP 요청 - 기본, 헤더 조회 (147)](#http-요청---기본-헤더-조회-147)
    - [HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form (149)](#http-요청-파라미터---쿼리-파라미터-html-form-149)
    - [HTTP 요청 파라미터 - @RequestParam (152)](#http-요청-파라미터---requestparam-152)
    - [HTTP 요청 파라미터 - @ModelAttribute (157)](#http-요청-파라미터---modelattribute-157)
    - [HTTP 요청 메시지 - 단순 텍스트 (159)](#http-요청-메시지---단순-텍스트-159)
    - [HTTP 요청 메시지 - JSON (163)](#http-요청-메시지---json-163)
    - [응답 - 정적 리소스, 뷰템플릿 (168)](#응답---정적-리소스-뷰템플릿-168)
    - [HTTP 응답 - HTTP API, 메시지 바디에 직접 입력 (171)](#http-응답---http-api-메시지-바디에-직접-입력-171)
    - [HTTP 메시지  (174)](#http-메시지--174)
    - [요청 매핑 핸들어 어댑터  (178)](#요청-매핑-핸들어-어댑터--178)
    - [정리(스프링MVC 기본기능)](#정리스프링mvc-기본기능)
  - [7. 스프링 MVC - 웹 페이지 만들기](#7-스프링-mvc---웹-페이지-만들기)
    - [프로젝트 생성(웹 페이지 만들기)](#프로젝트-생성웹-페이지-만들기)
    - [요구사항 분석 (185)](#요구사항-분석-185)
    - [상품 도메인 개발 (189)](#상품-도메인-개발-189)
    - [상품 서비스 HTML (193)](#상품-서비스-html-193)
    - [상품 목록 - 타임리프 (200)](#상품-목록---타임리프-200)
    - [상품 상세 (205)](#상품-상세-205)
    - [상품 등록 폼 (208)](#상품-등록-폼-208)
    - [상품 등록 처리 - @ModleAttribute (210)](#상품-등록-처리---modleattribute-210)
    - [상품 수정 (214)](#상품-수정-214)
    - [PRG Post / Redirect / Get (217)](#prg-post--redirect--get-217)
    - [RedirectAttribute (220)](#redirectattribute-220)
    - [정리 (웹 페이지 만들기)](#정리-웹-페이지-만들기)
  - [Q&A](#qa)

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

> view resolver 될때는 HttpServletResponse가 사실 별 필요가 없는거 같음. 

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

- Model은 HttpServletRequest 객체를 사용한다. request는 내부에 데이터 저장소를 가지고 있는데,
  - request.setAttribute() , request.getAttribute() 를 사용하면 데이터를 보관하고, 조회할 수 있다.

### MVC 패턴 - 한계 (65)

- 👍👍 **jsp를 통해서 html page를 반환하기 때문에 HttpServletResponse를 쓰지 않고 있음** 

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
  - **차이점은 jsp로 원래 request, response를 직접 넘기는거 대신, 필요한 data만 넘김**
  - 대신 MyView는 frontController에서 사용

- 뷰 리졸버
  - jsp의 디렉토리가 변경되었을때, 개별 controller를 손보지 않아도 된다.

### 단순하고 실용적인 컨트롤러 - v4 (89)

- ModelView를 쓰지 않음. process의 결과를 param으로 전달되는 map에 추가되게 함

- 문제점이 남음
  - ControllerV4말고 ControllerV1 의 인터페이스로 개발하고 싶을때 해결할 방법이 없다
  - > adapter 개념으로 해결한다고 한다.

### 유연한 컨트롤러1 - v5 (95)

- adapter 패턴을 적용한다고 하는데, 약간은 전통적인 어댑터 패턴과는 다른거 같음
  - 일단 impl해야하는 interface가 약간 인위적임 ( 기존 target이 아니라, 만든것)
  - 어댑티를 어댑터가 가지고 있지는 않음
  - 즉, 원래 target에 안들어 가는 어댑티를 위한 어댑터 개념은 아님
  - 핵심은 ModelView class를 어떤 controller든 반환하게 해주는것.
  - > 근데 얼추 어댑터 느낌이긴함 -> 맞긴한듯

### 유연한 컨트롤러2 - v5 (103)

- controllerv4를 위한 어댑터 추가
  
### 정리 (106)

- annotation을 지원하는 핸들러 어댑터를 만들면 spring-mvc와 같아진다.
  - > v5까지 진행한것을 보면 controllerv3,v4를 위한 핸들러 어댑터를 만들었었음.
- spring-mvc에서도 어댑터가 매우 중요함
  - ex: `@RequestMapping("/hello)` 어노테이션을 처리하는 어댑터는?
  - RequestMappingHandlerAdapter

## 5. 스프링 MVC - 구조 이해

### 스프링 MVC 전체 구조 (107)
  
- 스프링 부트는 DispacherServlet 을 서블릿으로 자동으로 등록하면서 모든 경로( urlPatterns="/" )에 대해서 매핑한다.
  - 즉 `@WebServlet`안붙여져 있지만 자동으로 등록되어 있음
- DispatcherServlet의 doDispatch가 핵심
  - 위에서 만들었던 FrontControllerServlet의 service method에 거의 대응되는 듯
    - pdf에 정리되어있다.

### 핸들러 매핑과 핸들러 어댑터 (112)

현재는 어노테이션 방식을 쓰지만, 일단 옛날방식으로 controller를 만들어 본다.  
  
- 예시에서는 `@Component("/springmvc/old-controller")`로 컨트롤러를 등록함
  - 실행 절차
  - spring-boot는 기본적으로 HandlerMapping, HandlerAdapter를 자동으로 등록해준다. ( 꽤 많은 것들을 등록해줌)
    - > 핸들러 매핑은 Mvc 만든예제처럼, controller와 url의 매핑 느낌
  - 아래는 등록된 것들 중 핵심적인거 소개
  - **HandlerMapping**
    - 0순위 = RequestMappingHandlerMapping : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
    - 1순위 = BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾는다. -> 위 예시처럼 url이름으로 bean이 등록되어 있어야한다.
      - > 예시는 여기에 걸림
  - **HandlerAdapter**
    - 0 = RequestMappingHandlerAdapter : 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
    - 1 = HttpRequestHandlerAdapter : HttpRequestHandler 처리
    - 2 = SimpleControllerHandlerAdapter : Controller 인터페이스(애노테이션X, 과거에 사용) 처리
  - pdf에 설명 되어 있음


적확한 내용은 https://mangkyu.tistory.com/180  에 있다.


### 뷰 리졸버 (117)

pdf참조.
  
- 뷰 리졸버도 만들어서 bean으로 등록할수 있다.
  - > thymeleaf 뷰 템플릿의 경우 옛날에는 ThymeleafViewResolver를 bean으로 등록해야 했었다.
  - > bean 등록은 configure 파일만들어서 `@bean`어노테이션을 메소드에 붙여서 만드는것.

### 스프링 MVC - 시작하기 (120)

- `@Controller`
  - 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식
    - > 즉 RequestMappingHandlerMapping에 의해서 등록되는 핸들러라는 말
  - 이거 대신 `@RequestMapping`을 class에 붙여도 된다. 이때는 `@Component`를 따로 또 붙여서 bean에 등록되게 해야함

method에 붙는 `@RequestMapping`는 소개는 안해주던데 pdf에 간략히 나오긴 함.. --> 다음 챕터 내용
대충 class가 controller로 등록되었으면, url path 처리에 매핑하는 method라고 생각하면될거 같다.
> 근데 return은 ModelAndView 여야하나??  param도 HttpServletRequest를 사용하기도 하였다. 아예 param이 없는경우도 있고
> return에 따라 view리졸버가 달라지는건가? 아님 어댑터 기능인가? (어댑터는 RequestMapping이면 RequestMappingHandlerAdapter를 쓰니까..)

### 스프링 MVC - 컨트롤러 통합 (125)

- `@RequestMapping`이 method 단위로 가능하므로, 기존과는 다르게 하나의 class에 모든 내용을 넣을수 있다.
  - > 보통 플젝이 이런식인듯

- 아직 ModelAndView 객체를 return해야 하는게 불편 --> 당음 챕터에서 해결

### 스프링 MVC - 실용적인 방식 (127)

- `@RequestMapping` 를 method에 썻을때는 get/post 구분없이 모두 받을수 있었음. 이게 싫으면
  - `@RequestMapping(value = "/new-form", method = RequestMethod.GET)` 나 `@GetMapping`를 대신 쓰면된다.
- `@RequestParam`
  - method에 파라메터를 HttpServletRequest로 받는거 대신 프론트컨트롤러 v3처럼 값으로 받을수 있음. 그게 위 어노테이션
  - v4처럼 response대신 Model을 받을수도 있음
    - > 이건 템플릿 엔진에서 쓰기 위한 값들.. 이라고 보면됨. 프론트컨트롤러에서도 그랬듯.
  - @RequestParam("username") 은 request.getParameter("username") 와 거의 같은 코드라 생각하면 된다.

- method return시 뷰네임(논리이름)을 직접 반환할수 있다.
  - v4처럼
  - `@RequestMapping` 어노테이션 기반 컨트롤러(핸들러)가 매우 유연함
    - > 아마 어댑터가 잘되어 있어서 그런듯?

http 스펙에서는 get은 사이드 이펙트가 없다고 생각한다. 따라서 get이면안되는 것들은 어노테이션을 달아서 post로만 받게 해야 한다.  
  
> 여기 내용이 실무에서 쓰는 방식

### 정리(스프링MVC)

method에서 return하면 viewResolver가 동작하는데. jsp의 경우 내부적으로 jsp (request) forwarding이 되고  
타임리프의경우는 내부적으로 직접 렌더링이 되어 html이 반환됨

## 6. 스프링 MVC - 기본 기능

### 프로젝트 생성 (130)

- war의경우 jsp가 지원됨. webapp경로 사용. 외부 서버에 배포목적으로 사용 보통..
  - 그래서 요즘은 그냥 jar로 프로젝트를 만들면된다.

- <https://docs.spring.io/spring-boot/docs/2.5.9/reference/html/features.html#features.developing-web-applications>
  - 웰컴페이지 내용
  - > 보니까 가이드 문서 보는 법이 있네.

### 로깅 간단히 알아보기 (135)

-`@RestController`

- 일반 컨트롤러는 method 반환을 뷰의 이름으로 간주함. restcontroller는 return을 body에 그냥 넣어줌

- 올바른 로그 사용법 중요
  - 로그 문자열에 +를 써서 하는것은 안좋다.
    - 이러면 로그 메소드에 param 문자열 연산이 진행되고 method로 전달됨
    - 근데 log level에 따라서 수행되지 않는 method의 param도 연산되는것은 매우 비효율적
  - 올바른 사용법
  - `log.debug("data={}", data)`

- 장점
  - 시스템 아웃 콘솔에만 출력하는 것이 아니라, 파일이나 네트워크 등, 로그를 별도의 위치에 남길 수 있다. 특히 파일로 남길 때는 일별, 특정 용량에 따라 로그를 분할하는 것도 가능하다.  
  - 뿐만아니라 압축 및 백업, 로그 파일은 10개만 유지 등의 기능도 있음
  - 성능도 일반 System.out보다 좋다. (내부 버퍼링, 멀티 쓰레드 등등) 그래서 실무에서는 꼭 로그를 사용해야 한다
    - > 실제 test시 수십배 성능 차이 남

pdf에 좀더 스터디 할수 있는 링크 있다.

### 요청 매핑 (138)

url이 method에 매핑 시키는 방법들을 소개.
pdf에 색깔 칠해논거 있음. 중요  
요새 **PathVariable**(경로 변수) 매핑 많이 씀. 이거 매우

- RequestMapping 은 URL 경로를 템플릿화 할 수 있는데, `@PathVariable` 을 사용하면 매칭 되는 부분을 편리하게 조회할 수 있다.
  - `@GetMapping("/mapping/{userId}") // url 경로를 template화 함`
- `@PathVariable` 의 이름과 파라미터 이름이 같으면 생략할 수 있다
  - `@PathVariable String userId 이렇게 쓸수 있다는것`
  - `@PathVariable` 어노테이션을 생략하면안됨 --> 근데 뭔가 이거 관련 내용이 뒤에 나오나봄

- 특정 파라미터 조건 매핑
  - 잘 쓰지는 않음. 코드 참조
- 틀정 헤더 조건 매핑
  - > 이건 플젝에 쓸만하겠는데?
- 미디어 타입 조건 매핑 - HTTP 요청 Content-Type, consumes
  - 이건 헤도 조건 매핑을 이용해서는 안된다. 스프링이 따로 더 처리해주는게 있다.
  - 미디어 타입에 따라 매핑 해주고 싶을떄 사요

- 미디어 타입 조건 매핑 - HTTP 요청 Accept, produce
  - 이건 HTTP 요청이 Accept랑 같이 왔을때 세팅되는것

### 요청 매핑 - API예시 (144)

배운거 사용해 보는 장
  
- content-type에 따라 error 보내는게 다르다.
  - html 보내거나, json 보내거나..
  - > 이거 error 보내는거 핸들링 가능? 현재는 spring이 defualt로 data를 보내주는데..

### HTTP 요청 - 기본, 헤더 조회 (147)

- pdf에 Controller의 사용가능한 파라미터 및 응답  목록 link가 나옴
  - 즉 request, response, HttpMethod 등.. 즉 method의 param으로 받을수 있는것들 나열됨
  - 어노테이션 기반의 컨트롤러라서 따로 method에 정해진 interface가 없어서 여러 param을 원하는 만큼 쓸수 있는게 장점.
  - > webRequest랑 NativeWebRequest는 요즘은 안쓴다네.. 회사선 쓰더만..
  - > return도 여러 type이 가능한데 DeferredResult 같은 비동기 return 잘 안쓴다네.. ( 아마 대신 async를 쓸듯)

- error관련내용은 validation에서 나온다는데.. 이거 mvc2임.

### HTTP 요청 파라미터 - 쿼리 파라미터, HTML Form (149)

간단한 내용  

- `response.getWriter().write("ok"); // 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X`
  - 이것만 특징적이 었음

### HTTP 요청 파라미터 - @RequestParam (152)

- `@ResponseBody`
  - 그냥 @Controller에서 String return method는 해당 return을 뷰의 논리 path로 보고 찾는다.
  - http body에 return string을 넣어 주고 싶다면, @RestController를 쓰던지, 아니면 method에 @ResponsBody 어노테이션을 써준다.

- `@RequestParam("username") String memberName`
  - 여기서 변수명이 username이었다면 `@RequestParam  String username` 이렇게 사용가능
  - 즉, 쿼리파라메터랑 변수명을 맞추면 RequestParam의 param생략가능
  - **이때 변수가 String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능**
  - > 근데 강사는 @RequestParam까지 생략하는 것보다는 넣는게 좋다고 함

- `@RequestParam(required = true)`
  - required 가 true면 반드시 있어야 하는값. default가 true라 true를 쓰고 싶다면 생략가능
  
pdf의 주의사항 참조 필요!  
  
- defaultValue
  - 사실 이걸 쓰게 되면 required는 있던 없던 상관이 없다.
  - 빈문자도 defaultValue로 치환해준다.
  - 이거 쓰면 변수 type에 int를 써도 문제 없다.
    - > 위에서는 문제가 있었음. required=false일때 해당 값에 null을 spring이 넣어주는데 primitive에는 null이 안들어가서 오류남

- `@RequestParam Map<String, Object> paramMap`
  - 파라미터의 값이 1개가 확실하다면 Map 을 사용해도 되지만, 그렇지 않다면 MultiValueMap 을 사용하자
  - > 보통근데 파라미터는 값을 하나만 넣는다.

다음 절은 파라메터를 객체로 바꾸는 쉬운 방법 소개  
> 프론트컨트롤러 만들땐 request param에서 값꺼내서 member객체를 만들었었음  
> 서블릿에서는 objectMapper를 이용해서 변환하기도 했었고..

### HTTP 요청 파라미터 - @ModelAttribute (157)

- @ModelAttribute
  - 이거 request param을 객체에 직접 매핑해줌
  - **원리는, request param에 해당하는 프로퍼티가 있는지 변수 type class에서 찾고 있으면 넣어준다**.
    - 👍 즉 class의 변수 이름과 request param의 이름이 같아야함
    - > 프로퍼티는 python에서 나왔던거랑 같음.. get,setter있는 변수.
  -  model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때자세히 설명

- 바인딩 오류
  - 이건 validation에서 나온다고 함
  - > 오류 처리 및 검증이  controller에서 빡센코드..

- @ModelAttribute 는 생략할 수 있다. 그런데 @RequestParam 도 생략할 수 있으니 혼란이 발생할 수 있다.
  - 스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
  - String , int , Integer 같은 단순 타입 = @RequestParam
  - 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
    - argument resolver는 HttpRequestServlet 같은 type을 말함. 또는 내가 만든 class도 argument resolver로 지정가능
    - 근데 기본적으로 내가 만든 class를 method의 param에 사용하면 ModelAttribute가 붙는다고 생각하면됨
- ModelAttribute에 name field
  - 이건 뷰가 나와야 이해하기 좋다. 그래서 다음 챕터에서 웹 프로젝트 만들며 설명한다.

여기까지 request가 json으로 오는 경우 빼고 다 소개함 ( get, form으로 오는 post)
  
### HTTP 요청 메시지 - 단순 텍스트 (159)
  
> 이번 챕터 중요함

- 최근 http spec에서는 get에도 body에 data넣을수 있는데 실무에서는 이렇게 잘안씀

- http message converter라는 기능이 핵심
  - > 아래 HttpEntity이거인거 같고, 이것도 귀찮으니까 RequestBody,ResponseBody 어노테이션이 제공된듯
  - json이나 text로 오는 body data를 아주 쉽게 핸들링할수 있음

- HttpEntity
  - HTTP **header**, body 정보를 편리하게 조회
  - 요청 파라미터를 조회하는 기능과 관계 없음 @RequestParam X, @ModelAttribute X
- HttpEntity는 응답에도 사용가능
  - 즉, method return type으로 쓰는게 가능
  - 메시지 바디 정보 직접 반환
  - 헤더 정보 포함 가능
  - view 조회X
  - > 코드를 보면 이해가 쉬움. 이거 없을땐 `return void에 responseWriter.write("ok");`를 썻음

- HttpEntity 를 상속받은 다음 객체들도 같은 기능을 제공한다
  - RequestEntity
    - HttpMethod, url 정보가 추가, 요청에서 사용
  - ResponseEntity
    - HTTP 상태 코드 설정 가능, 응답에서 사용
    - `return new ResponseEntity<String>("Hello World", responseHeaders,HttpStatus.CREATED)` -> 상태코드 세팅
  - > RequestBody나 ResponseBody랑은 다름

- @RequestBody
  - @RequestBody 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다.
  - 참고로 헤더 정보가 필요하다면 HttpEntity 를 사용하거나 @RequestHeader 를 사용하면 된다.
  - 이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam , @ModelAttribute 와는 전혀 관계가 없다.
    - > 이건 get이나 post form을 위한 기능
  - > 이거 중간에 message converter라는 메커니즘이 동작. 그래서 원래 우리가 해야하는 inputstream에서 string으로 변경하는 것을 자동으로 해줌
  - > 아래 절을 보니까 String으로 변환해준건 type을 String으로 써서 그런듯.. class면 json sting body를 class에 맞춰줌
    - > 마치 request param에서 modelattribute 처럼

- **정리**
  - 요청 파라미터 vs HTTP 메시지 바디
  - 요청 파라미터를 조회할때 사용: @RequestParam , @ModelAttribute
  - HTTP 메시지 바디를 직접 조회할때 사용: @RequestBody

### HTTP 요청 메시지 - JSON (163)

> 이번장 매우 중요

- `@RequestBody HelloData helloData`
  - 이거가 중요한건데 이건 `HttpEntity<HelloData>` 랑 같음
  - 마치 request param의 modelattribute처럼 객체로 내용을 받을수 있음

- 원리
  - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
  - 즉, message type이 json이면 위 컨버터가 동작하는데 내용은
    - `HelloData data = objectMapper.readValue(messageBody, HelloData.class);` 와 동일하다고함

- 이때 @RequestBody는 생략 불가능
  - 생략하면 @ModelAttribute가 붙음

- **스프링은 @ModelAttribute , @RequestParam 해당 생략시 다음과 같은 규칙을 적용한다.**
  - String , int , Integer 같은 단순 타입 = @RequestParam
  - 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
  - > 앞서 한번 설명했음

- @ResponseBody
  - 이거 사용시 return type에 HelloData 같은 class 넣을수 있음.
  - 이러면 message conveter가 적용되어서 객체 내용을 json으로 변경해서 body에 넣어줌
  - 즉 RequstBody에 사용되었던 message conveter가 나갈때도 적용됨
    - > content-type을 보고 spring이 MappingJackson2HttpMessageConverter를 선택했었음
    - > client에서 (Accept: application/json) 가 나가는 conveter에 영향을 준다고 함..
  - 이떄도 HttpEntity 사용가능

### 응답 - 정적 리소스, 뷰템플릿 (168)

- 응답은 3가지 방식이 있음
  - 정적 응답
  - 뷰템플릿
  - json같은거 반환 (다음 절에서 나옴)

- templates/response/hello.html
  - spring은 templates 가 뷰템플릿 위치
  - 논리 path를 쓰면 templates/ 및 .html은 뷰리졸버가 붙여줌 ( 이거 세팅가능 )
    - > pdf의 Thymeleaf 스프링 부트 설정. 추가 설정 link도 있음

- method에서 void 반환시 뷰템플릿 어떤거 쓰는지?  
  - pdf참조. 또는 코드 참조
  - 일단 url 매핑된것을 논리 Path로 쓰는데 조건이 있음
  - **참고로 이 방식은 명시성이 너무 떨어지고 이렇게 딱 맞는 경우도 많이 없어서, 권장하지 않는다.**
  - > 응답은 v2방식 정도를 권장

### HTTP 응답 - HTTP API, 메시지 바디에 직접 입력 (171)

- ResponseBody를 쓰면 상태값 세팅은 안됨
  - 이경우 따로 annotation을 통해 상태값 세팅
    - **애노테이션이기 때문에 응답 코드를 동적으로 변경할 수는 없다. 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity 를 사용하면 된다.**
    - > ResponseEntity이거 HttpResponseServlet 받아서 writer 쓰는거랑 동일한 개념이었음
    - > ResponseEntity 반환하는게 살짝 이해가 안가는데, 프론트컨트롤러 만들때는 논리path를 넘기는 형태 즉, 응답3가지 방식중 2번째를 기준으로만 작업을 했음

- ResponseBody를 method 레벨에서 붙이기 귀찮으면 class level에 붙여도 된다.
  - 이경우 method return을 주의 해야함 ResponseEntity return은 문제 없는데, 그냥 string return을 논리path로 썻다면 이건 body에 string으로 들어가게됨.
    - 즉, 원치 않는 동작
  - **ResponseEntity** 붙는것들은 문제 없음
    - 이건 직접 body에 값을 채우는거니까
    - > ~~아마 컨버터가 동작하진 않을듯~~ 틀림 HttpEntity 류는 컨버터 동작함.

- RestController
  - Controller, ResponseBody 를 class level에 붙인거랑 동일한 결과를 준다.
  - http api (rest api) 만들때는 이거 많이 씀
    - > 상태값은 위 내용 처럼 ResponseEntity 를 return 하게 하면되는듯

- 의문
  - @ResponseBody는 client의 accept에 영향을 받는다고 했었는데.. accept가 없으면 어찌 반응하지?
  - `@PostMapping(value = "/mapping-produce", produces = "text/html")` 이런 구문도 있었음
    - > 이걸로 producse를 이용해서 json컨버터 강제 세팅가능한가?

### HTTP 메시지  (174)

- @ResponseBody 원리 개요
  - HTTP의 BODY에 문자 내용을 직접 반환
    - > 서블릿에서는 writer를 만들어서 값을 썻었음
  - viewResolver 대신에 HttpMessageConverter 가 동작
  - 기본 문자처리: StringHttpMessageConverter
  - **기본 객체처리: MappingJackson2HttpMessageConverter**
  - byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음

- **스프링 MVC는 다음의 경우에 HTTP 메시지 컨버터를 적용한다.**
  - HTTP 요청: @RequestBody , **HttpEntity**(RequestEntity) ,
  - HTTP 응답: @ResponseBody , **HttpEntity**(ResponseEntity)

- HTTP 메시지 컨버터는 HTTP 요청, HTTP 응답 둘 다 사용된다
  - 그래서 read, write method가 interface에 정의 됨
  - > canRead, canWrite같은 메소드 존재. 조건 확인 위해서

- 스프링 부트는 다양한 메시지 컨버터를 제공하는데, 대상 **클래스 타입**과 **미디어 타입**둘을 체크해서 사용여부를 결정한다.
  - 만약 만족하지 않으면 다음 메시지 컨버터로 우선순위가 넘어간다.
  - 기본적으로 http 이용시 content-type을 명시해 줘야함
    - > 즉 iot 기기에서도 http 이용하고 있었다면 content-type은 줬겠네. 이거 중요 point

- 컨버터가 보는 미디어 타입
  - HTTP 요청일땐 Content-Type 미디어 타입을 봄
  - HTTP 응답일땐 Accept 미디어 타입을 봄
    - **@RequestMapping 의 produces 가 주어진다면 이걸로 판단**

- 여튼 핵심은 ResponseBody, RequestBody 사용시 컨버터의 조건에 따라서 해당 컨버터 사용이 결정된다는것
  - 이때는 변수의 클래스 타입 및 req/resp의 미디어 타입이 영향을 준다. ( resp 미디어 type은 client의 accept의 영향도 받는다. )
  - 컨버터마다 조건은 pdf참조

> 예시는 pdf에 있음

### 요청 매핑 핸들어 어댑터  (178)

- @RequestMapping 기반의 컨트롤러는 RequestMappingHandlerAdapter 가 처리한다.
  - 해당 어댑터는 아래 두개를 사용해서 핸들러(컨트롤러)를 호출
    - ArgumentResolver
      - @RequestBody, HttpServletRequest, Model, RequestParam 등을 처리해서 필요한 object를 만들어서 준다.
      - 이걸 핸들러로 넘김
      - 주의 : 메시지 컨버터는 RequestBody, HttpEntity 등일때 동작. 컨버터의 read호출
    - ReturnValueHandler
      - ModelAndView, @ResponseBody 등을 처리
      - 메시지 컨버터의 write를 호출 ( 역시 메시지 컨버터 동작하는 type은 따로 존재)
    - > method의 가능한 param, return 값은 pdf에 링크가 있음 (이거 앞절에서도 나왔었음 )

- 메시지 컨버터
  - RequestBody, HttpEntity 등을 사용시 컨버터가 동작하는데 해당 컨버터는 ArgumentResolver,ReturnValueHandler 에서 사용함

- 스프링 MVC는 @RequestBody @ResponseBody 가 있으면 RequestResponseBodyMethodProcessor (ArgumentResolver) 사용
  - 이거 ArgumentResolver,ReturnValueHandler 두개의 기능이 하나로 합쳐저 있음

- WebMvcConfigurer
  - 기능확장은 WebMvcConfigurer를 상속 받아서 스프링 빈으로 등록하면된다.
  - > addArgumentResolvers 같은 method가 있는것으로 봐서 ArgumentResolver 같은거 만들어서 addArgumentResolvers로 오는 list에 추가해주면 되는거 같음

### 정리(스프링MVC 기본기능)

- 응답 3가지 처리
  - get, post(form) : RequestParam
  - json : http body를 처리해야하므로 message converter를 사용

- json 받을떄 : content-type이랑 class 를 검토
- json 보낼떄 : **accept, produces, class 를 검토**

- @ModelAttribute 생략시 내용 쫌 이상함
  - argumentResolver로 지정해둔 type외는 전부 ModelAttribute 라고 했는데.
  - ModelAttribute도 argumentResolver가 핸들링하는거 같은데.. 그럼 message converter랑 연관있다고 해야 했던거 아닐까?

- message converter
  - http body 처리를 json으로 해주는 컨버터
  - string으로 해주는 컨버터
  - bytearray로 해주는 컨버터

## 7. 스프링 MVC - 웹 페이지 만들기

### 프로젝트 생성(웹 페이지 만들기)

### 요구사항 분석 (185)

### 상품 도메인 개발 (189)

- 멀티 쓰레드 관련 주의사항 중요 (코드에 적음)
  - ConcurrentHashMap, AtomicLong을 사용해야한다
- method 개발시 정석 방법 중요
  - DTO를 써야하는 case
  - 중복 vs 명확성. 항상 명확성이 나음
    - > 코드에서 보면 DTO를 만들면 Item class랑 중복이 쫌 생김. 근데 그래도 명확성이 좋기 때문에 이게 정석적으로는 낫다고 함

- 테스트의 package 경로를 src랑 맞춰주는 이유가 뭐였지?
  - 아마 import때문인거 같긴한데.. 같은 package여야 사용가능하거나. 뭐 그런...등등
  - > integration test 짤때는 그럼 어떻게? test package 경로를 잡지?

### 상품 서비스 HTML (193)

- css 파일 넣고. url로 접근 해봤을때 안보이면 intellij의 out 폴더 지우고 해보면 잘된다.
- 정적 리소스는 get으로만 받을수 있다.
  - post로 진행하면 spring에서 막음

### 상품 목록 - 타임리프 (200)

- spring에서 contructor 하나만 있을때 param으로 빈등록 해주던거.. 다시 확인 필요
  - param에 빈만 들어가야 하는지? -> 아마 그래야 할거 같긴한데.. 다른 값을 bean 등록때 넣어 줄수가 없으니

- templates 폴더는 view template을 위한것
  - static은 정적배포
  - method에서 return으로 view path주면 당연히 templates 폴더를 root로 뒤질듯
  
이번 절은 타임리프 문법 소개임  
  
### 상품 상세 (205)

- 상품수정을 위한 url은 edit라고 해주던데..
  - 이거 그냥 http put같은거를 이용하게 할수는 없나?
  - `location.href` 이거는 항상 get일까? -> 타임리프로는 안되나봄.
  - 아마 이건 rest api 형태는 아니겠지?
    - 원래 itme 수정은 put으로 하는거 같은데 여기서는 url edit을 따로 만들었으니..

### 상품 등록 폼 (208)

- 같은 url을 get/post에 따라 기능을 나누어 개발하는게 좋다.
- th:action에서 값을 안넣으면 현재 url로 post 전송이 된다. ( form에 post 세팅시 )

### 상품 등록 처리 - @ModleAttribute (210)

> 이거 나중에 헷갈릴 수도 있을거 같음. 주의깊게 봐야함 👍

- `@ModelAttribute("item")` 이렇게 쓰면 model에 item을 키로 해당 값을 넣어줌 👍👍
  - name을 생략하면 변수 type(class)이름의 첫 대문자만 소문자로 바꾼 문자열을 key로 사용함
  - > pdf 또는 코드 참조

- @ModelAttribute를 생략하는 버전의 코드도 있는데 이렇게 까지 쓰는건 회의적 ( 그래도 쓸때도 있고 아닐때도 있다고 함)

### 상품 수정 (214)

- redirect 세팅해서 응답을 보내면 받는측은 302 상태코드를 받음 ( 아마 spring이 세팅해서 보낸듯, 상태코드는.. )
- redirect관련 자세한 내용은. http 강좌에 있다고함
  - > 이거 한번 들어보는것도 좋겠는데..

- HTML Form 전송은 PUT, PATCH를 지원하지 않는다. GET, POST만 사용할 수 있다.
  - PUT, PATCH는 HTTP API(rest api) 에 사용   
  - HTTP POST로 Form 요청할 때 특정 히든 필드에 값을 세팅하면, spring은 해당 form처리를 PUT, PATCH 매핑을 통해 하기도함 
    - 근데 어쨋든 client에서  HTTP 요청은 POST로 진행

**왜 상품등록에는 redirect를 안쓰고 수정에만 썻지??**
  
위 내용이 다음 절에 나온다. ( 결론은 일부러 설명하려고 문제점을 남긴거 )

### PRG Post / Redirect / Get (217)

- 기존 addItemV4 같은경우 사용자가 post 로 form을 보내주면 처리후 basic/item.html을 렌더링 해서 응답의 body에 넣어서 전달해줌
  - 사용자의 마지막 상태는 post 메시지 전달하던 상태임 (url도 그렇고, cache된 입력 data도 그렇고..)
  - 왜냐면 다른 페이지에 간게 아님. 응답을 받았을뿐

- PRG 패턴은 실무에서 많이씀. 즉 Post 메시지를 받았으면 redirect를 넣어서 응답해주는것. 그럼 client는 다시 get으로 redirect page를 받아감

**아직 아래와 같은문제가 있음**  
  
- `return "redirect:/basic/items/" + item.getId()` 
  - redirect에서 `+item.getId()` 처럼 URL에 변수를 더해서 사용하는 것은 **URL 인코딩이 안되기 때문에 위험하다.**
  - 다음에 설명하는 RedirectAttributes 를 사용하자.

### RedirectAttribute (220)

- 타입리프에서는 `${param.status}`처럼 쿼리 파라메터를 쉽게 핸들링 할수 있는 param변수를 제공 
  - > 쿼리 파라메터도 Model에 추가되서 오던가?? --> nope
  - > 즉 뷰리졸버 돌때 서블릿rq랑 response랑 모두 넘어가는 형태 같음 --> 맞는거 같음
  - > 프론트 컨트롤러 만들때 보면 view rendering할때 req, res 모두 넘김 ( 짜피 spring-mvc도 서블릿이용한 거일테니..)
  - https://dololak.tistory.com/502
    - requestDispatcher가 핵심임

쨋든 프론트핸들러 만들던 내용이 핵심이네 👍

### 정리 (웹 페이지 만들기)

## Q&A

- DAO는 데이터 접근 객체 그러니까 DB 같은 곳에 접근하는 객체를 뜻합니다. 일반적으로 DAO안에 DB에 접근하는 코드들이 모여있습니다.
  @Repository는 도메인 객체를 저장하고 관리하는 저장소 역할을 뜻합니다. 구현 보다는 도메인 객체를 관리하는 역할에 초점이 맞추어져 있습니다.
  SQL을 직접 다룰 때는 쿼리 중심이고, 도메인 객체라는 것이 명확하게 없지만,
  JPA를 사용하면 아무래도 명확하게 도메인 객체를 정하고(보통 엔티티로) 해당 도메인 객체들을 관리하기 때문에 Repository라는 단어가 더 맞다 생각합니다.