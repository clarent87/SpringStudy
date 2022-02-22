package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

// 따로 test관련 어노테이션 없어도되네.. junit 같은거 원래 넣어주지 않았었나??
public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodeResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item","required");
    }

    @Test
    void messageCodesResolverField() {
        // field를 이용하는 경우는 String.class까지 넣어주어야함. 생성된 배열 잘 볼것
        // "required.java.lang.String" 는 String일 경우 어떤 message를 줄때 사용 가능 (properties 파일에 이걸로 명시 하면되니까..)
        // 아래 코드를 bindResult.rejectValue() 안에서 실제로 사용한다.
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
