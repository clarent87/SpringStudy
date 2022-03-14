package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v1")
public class ServletUploadControllerV1 {

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/uplaod")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request); // 알던 request 객체 타임이 아님.
                                            // 원래 톰캣 기본 구현체는, RequsetFacade였다. 근데 여기서는 StandardMultiPartSevletRequest가옴

        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName); // 멀티파트로 들어온건데 param이 잘 받아 졌음

        Collection<Part> parts = request.getParts(); // 이걸로 multipart를 받음
        log.info("parts = {}", parts);

        return "upload-form";
    }
}
