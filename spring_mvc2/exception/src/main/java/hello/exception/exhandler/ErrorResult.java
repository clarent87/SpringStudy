package hello.exception.exhandler;


import lombok.AllArgsConstructor;
import lombok.Data;

// 오류가 발생했을떄 보낼 json data
@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;
}
