package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Data 비지니스 로직에 핵심적인 domain에 Data를 쓰는것을 위험. 예기치 못한 동작할수 있다고함. ( data annotation이 만들어 주는게 많아서.. 잘알아봐서 써야함 )
// 단순하게 data를 왔다갔다 할떄 쓰는 dto에서만 쓰는게 좋다. (근데 이 상황에서도 잘 알아보고 써야함 )
@Getter @Setter
@NoArgsConstructor
public class Item {
    private Long id;
    private String itemName;
    private Integer price; // price가 안들어갈수도 있어서 Intger를 씀 null주려고
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
