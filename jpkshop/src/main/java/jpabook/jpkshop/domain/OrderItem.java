package jpabook.jpkshop.domain;

import jpabook.jpkshop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    // 주문 상품 엔티티
    // 주문과 item 다대다를 깨줄려고 만듬. + a data를 추가

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count;  // 주문 수량

    // jpa는 스펙상 protected 생성자 까지는 허용
    // 이거 만든 이유는 factory method 두었기 때문.. -> 즉 factory method 이외의 방법으로 객체를 만드는것을 제한해야한다
    // jpa쓰면서 protected둔건 이걸로 생성하지 말라는것을 명시적으로 알려주는 행위
    // @NoArgsConstructor(access = AccessLevel.PROTECTED)로 대체함
//    protected OrderItem() {
//    }

    //==생성 메서드==//
    // item에도 가격이 있지만, 할인 같은 행사가 있다면 가격이 달라지니까, orderPrice를 따로 받음
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비지니스 로직==//
    public void cancel() {
        getItem().addStock(count); // 재고 수량 원복
    }

    //==조회 로직==//
    public int getTotalPrice() {
        // TODO: 아예 접근을 getter로 진행함.. private 변수 인데.
        // 리팩토링에선 이거 쫌 과하다 하지 않았나? 근데 cancle함수는 또 그냥 변수로 진행했네..
        return getOrderPrice() * getCount();
    }
}
