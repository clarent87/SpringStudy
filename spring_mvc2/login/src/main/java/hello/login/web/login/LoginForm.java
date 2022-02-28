package hello.login.web.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


// 간단해서 web-item-form 처럼 form 패키지를 만들지는 않고 login 패키지밑에 바로 만듬
@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
