<%@ page import="hello.servlet.domain.mamber.Member" %>
<%@ page import="hello.servlet.domain.mamber.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--아래 tag안에서는 java 코드를 쓸수 있다.--%>
<%
    // jsp도 결국 servlet으로 변경되기 때문에, request, response 변수는 기본적으로 쓸수 있다. ( jsp문법상 지원됨 )

    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
서공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
