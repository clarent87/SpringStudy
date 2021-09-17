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

    // 취소

    // 검색
}
