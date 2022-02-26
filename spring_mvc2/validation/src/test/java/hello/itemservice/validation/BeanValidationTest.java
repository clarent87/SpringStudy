package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    // 스프링 없이 사용할땐 아래와 같이 쓰는데, 스프링에 통합되어 있어서 아래처럼 쓸필요가 이제는 없다
    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator(); // 이거 ItemValidator가 나오는거 아니다.. 당연하게도

        Item item = new Item();
        item.setItemName(" "); //공백
        item.setPrice(0);
        item.setQuantity(10000);

        Set<ConstraintViolation<Item>> violations = validator.validate(item); // item에 있는 어노테이션 기반 검증 됨
        for (ConstraintViolation<Item> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage()); // 오류 메시지는 세팅한 값은 아님. 하지만 세팅 가능
        }
    }
}
