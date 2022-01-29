package hello.core.Order;

public interface OrderService {
    // 문서의 "주문서비스역할" 화살표는 메시지니까 메시지 형태를 보고 그대로 작성
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
