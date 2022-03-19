package hello.upload.domain;

import lombok.Data;

import java.util.List;

@Data
public class Item {
    private Long id; // db 저장할때 자동으로 생기는 값. 이라고함
    private String itemName;
    private UploadFile attachFile;
    private List<UploadFile> imageFiles;
}
