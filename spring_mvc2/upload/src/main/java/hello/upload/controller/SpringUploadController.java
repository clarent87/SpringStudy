package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    // spring에서는 @RequestParam 로 parts의 각 part를 받을 수 있음.
    // binding은 당연히 <input name="itemName" type="text"></li> 에서 itemName 그리고 file. 이 원리는 그냥 쿼리 param 받는거랑 같음
    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName,
                           @RequestParam MultipartFile file, HttpServletRequest request) throws IOException { // HttpServletRequest 는 그냥 넣어 줬음

        log.info("request={}", request);
        log.info("itemName={}", itemName);
        log.info("multipartFile={}", file);

        if (!file.isEmpty()) {
            String fullPath = fileDir + file.getOriginalFilename();
            log.info("파일 저장 fullPath={}", fullPath);
            file.transferTo(new File(fullPath)); // Path param을 받는 버전의 transferTo도 있음
        }
        return "upload-form";
        //  return "redirect:/spring/upload"; PRG 패턴을 적용해 주면 좋다. ( 이건 질문란의 내용 )
    }
}


