package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


// 보면 domain에 있는 Item이랑은 다르다. 그래서 이렇게 DTO를 만드는것
// @ModelAtrribute로 ItemForm 받으면 아래 MultipartFile도 알아서 넣어짐.
// 아마 html post form에 아래 이름의 field들이 있을듯.
@Data
public class ItemForm {
    private Long itemId;
    private String itemName;
    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
