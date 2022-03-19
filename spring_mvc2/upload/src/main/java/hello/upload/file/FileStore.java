package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 여러 파일 저장
     * @param multipartFiles
     * @return
     * @throws IOException
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }

    /**
     * 파일 저장하고 UploadFile 객체 반환
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename(); // 사용자가 업로드한 파일 네임 -> image.png
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    /**
     * 서버에 저장하는 파일명 반환
     * 이때 중요한것 file의 확장자는 유지해 준다. -> 이래야 서버에서 관리하기 쉬움
     * 이것도 특화 기능이니 method로 뽑음 -> 이래야 코드 이해하기도 좋지
     * @param originalFilename
     * @return
     */
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    /**
     * 확장자 뽑는데 특화된 기능이니까 method로 뽑음
     * @param originalFilename
     * @return
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf("."); // string에서 . 문자가 마지막에 나온 index 찾기.
        return originalFilename.substring(pos + 1); // 해당 index 뒤문자열만 가져옴
    }

}
