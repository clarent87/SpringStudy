package hello.upload.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName; // 사용자가 업로드한 파일 네임
    private String storeFileName; // 시스템에 저장한 파일 네임
                                    // 사용자 A, B 가 같은 파일 이름을 올렸다면, storeFileName이 없다면, 파일이 덮여서짐
                                    // 따라서 store할땐 각각 다른 경로에 저장 필요 -> storeFileName는 UUID 같은걸 이용
}
