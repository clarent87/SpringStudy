package hello.hellospring.controller;

public class MemberForm {
    private String name;  // <input type="text" id="name" name="name" placeholder="이름을입력하세요"> 의 name과 변수명 일치 필요

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
