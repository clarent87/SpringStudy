package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request) {
        String data = request.getParameter("data");//문자 타입 조회
        Integer intValue = Integer.valueOf(data); //숫자 타입으로 변경
        System.out.println("intValue = " + intValue);
        return "ok";
    }


    // (1) 컨버터 등록 후 내 컨버터가 우선순위가 높아서 그게 적용됨. 즉 만든 컨버에 있던 log가 나옴
    // (2) 포매터 적용후 (컨버터는 주석처리) param으로 "10,000"을 주면 포매터가 동작해서 10000이 들어감 ( 원리는 포매터컨버전서비스Test와 동일한듯)
    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data) { // rq param의 이름이 data여야함
        System.out.println("data = " + data);
        return "ok";
    }

    // TODO: ModalAttribute로 받는거랑은 다르다. 그건 requsetParam 여러개 일때 객체에 담는것
    // 이건 단일 스트링을 쪼개서 IpPort 객체에 넣는것
    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort) {
        System.out.println("ipPort IP = " + ipPort.getIp());
        System.out.println("ipPort PORT = " + ipPort.getPort());
        return "ok";
    }

}
