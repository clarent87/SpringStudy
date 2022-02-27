package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range; // Range는 하이버네이트에서 제공하는것
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
// 오브젝트 에러 체크는 아래처럼 가능하긴 하지만.. 오바라고 함
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "10000원이 넘으면 안된다.")
public class Item {

    // 어노테이션은 다 막았음. 폼 별 별도객체를 만들어서 에러 처리하기 위함
//    @NotNull(groups = UpdateCheck.class)
    private Long id;

//    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Range(min=1000,max=10000)
    private Integer price;

//    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Max(value = 9999, groups = {SaveCheck.class})
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
