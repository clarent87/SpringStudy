package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) { // get일때도 modelAttribute 일부터 받음. 왜그런지는 mvc2 타입리프 스프링 통합에 나옴
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
        // file들은 storage에 저장한다. aws쓰면 s3에 저장
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        //데이터베이스에 저장 ( 파일이 저장 경로만 저장 )
        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storeImageFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}"; // 이건 client로 redirect 세팅되어 날아가는 거였지..
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    // 이미지 다운
    // 근데 아래 로직은 보안에는 취약하다고함. 그래서 check 로직 넣으면 좋다고함.
    // -> 무슨 보안에 취약하다는 거지?
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // 아래 경로 file에 접근해서 stream으로 반환해줌
        // "file:" prefix붙이면 UrlResource에서 내부 파일에 직접 접근해 준다고함
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
    
    // 첨부파일 다운로드. 이건 쫌더 복잡 ( 근데 logic은 downloadImage 이랑 크게 차이는 없음 )
    // @ResponseBody 써도 되는데,
    // 강의에서는 다양한 코드를 보여주기 위한 목적 + 헤더에 뭐 추가할게 있어서 ResponseEntity 씀.
    // itemId를 받은 이유
    // - 만약 itemId에 접근 할수 있는 user 체크하는 로직이 있다면, 해당 로직으로 접근 제한을 할수 있기 때문
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // 파일이름이 한글일떄 깨질까봐. 이거 해줌
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        
        // contentDisposition 헤더 세팅을 안하면, browser에서 그냥 해당 file을 열어서 보여준다. 
        // 이걸 세팅해야 파일이 다운로드됨
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }
}
