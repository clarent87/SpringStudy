package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range; // Range는 하이버네이트에서 제공하는것

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    private Long id;

    @NotBlank(message = "공백X") // 이렇게 메시지 세팅도 가능
    private String itemName;

    @NotNull
    @Range(min=1000,max=10000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
