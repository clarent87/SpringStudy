package jpabook.jpkshop.service;

import jpabook.jpkshop.domain.Delivery;
import jpabook.jpkshop.domain.Member;
import jpabook.jpkshop.domain.Order;
import jpabook.jpkshop.domain.OrderItem;
import jpabook.jpkshop.domain.item.Item;
import jpabook.jpkshop.repository.ItemRepository;
import jpabook.jpkshop.repository.MemberRepository;
import jpabook.jpkshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        // 원래는 생성한 delivery, orderItem도 persist를 통해서 db에 저장해야 한다.
        // 하지만 Order의 CascadeType.ALL 세팅한거 때문에 order를 persist 할떄 위 두개도 자동으로 persist된다.

        return order.getId();
    }

    /**
     * 주문 취소
     */

    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();

        // 보통은 위와 같이 하고
        // db를 업데이트하는 로직을 service 계층에 작성해줘야한다. (즉 transactional scirpt를 비지니스 계층에서 써야함.)
        // => code만 보면(위) 단순히 객체의 값을 수정하는 것밖에 없음. 즉 db update는 따로 필요.
        //    근데 이걸 jpa가 자동으로 해줌.
        //    즉, 엔티티 안에서 바뀐 포인트들을 jpa가 체크해서 db에 update -> this is 더티 체킹 (변경내역 감지)

        // TODO: save같은경우는 em에 없는 객체를 넣어주는거니까 persist한거고, cancel의 경우 em에서 가져온
        //       객체의 변경이라Update를 자동으로 해준듯..
    }

    // 검색
}
