package jpabook.jpkshop.domain.item;

import jpabook.jpkshop.domain.Category;
import jpabook.jpkshop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter // setter는 안두는게 좋다. setter대신 addStock같은거 호출하는게 좋다. TODO:이거 어디에 나와있었지
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity; // 재고 수량

    @ManyToMany(mappedBy = "items") // 카테고리의 items 필드
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }
}
