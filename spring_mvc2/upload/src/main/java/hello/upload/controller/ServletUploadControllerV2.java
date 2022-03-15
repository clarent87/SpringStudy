package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}") // application properties의 값을 이걸로 가져올수 있다.
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request); // 알던 request 객체 타임이 아님.
        // 원래 톰캣 기본 구현체는, RequsetFacade였다. 근데 여기서는 StandardMultiPartSevletRequest가옴

        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName); // 멀티파트로 들어온건데 param이 잘 받아 졌음

        Collection<Part> parts = request.getParts(); // 이걸로 multipart를 받음
        log.info("parts = {}", parts);

        for (Part part : parts) {

            log.info("==== PART ====");
            log.info("name={}", part.getName()); // multipart  <input name="itemName" type="text"> 의 name인듯.
            // part도 헤더와 body로 구분됨

            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("header {}: {}", headerName,
                        part.getHeader(headerName));
            }
            //편의 메서드
            //content-disposition; filename ==> part의 헤더에 있는 정보인데 직접 parsing하려면 피곤
            log.info("submittedFileName={}", part.getSubmittedFileName());
            log.info("size={}", part.getSize()); //part body size

            //데이터 읽기 (body내용. 여기서는 text 또는 file binary)
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // binary data를 string으로..
            log.info("body={}", body);

            //파일에 저장하기
            if (StringUtils.hasText(part.getSubmittedFileName())) { // file명 있는지 확인. 즉 content-type: image/png 일때.
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath={}", fullPath); // 저장 안되면 찾아야 해서 로그 필요.
                part.write(fullPath);
            }

        }
        return "upload-form";
    }
}
