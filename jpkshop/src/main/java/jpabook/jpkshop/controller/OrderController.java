package jpabook.jpkshop.controller;

import jpabook.jpkshop.domain.Member;
import jpabook.jpkshop.domain.Order;
import jpabook.jpkshop.domain.item.Item;
import jpabook.jpkshop.repository.OrderSearch;
import jpabook.jpkshop.service.ItemService;
import jpabook.jpkshop.service.MemberService;
import jpabook.jpkshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {
        // 다른 template과는 달리 orderForm에는 th:object가 없어서 위에 처럼, select값을 받는건가봄

        // member나 item을 여기서 찾아서 넘길수도 있지만.
        // 그런것 보다는 서비스 계층에서 해당 객체들을 찾는것이 좋다.
        // 여러 장점이 있음(9:22) -> order method caller에서 다른 세팅들을 할 필요 없기도 하고.. 단순 id만 넘기면되니까.
        // 그리고 Transactional에서 관련된 것들이 처리/다뤄지는게 좋음. order가 transactional method
        // 즉 영속성 연관 있는 것들은 Transactional에서 처리
        // command성 로직들 (즉, 조회가 아닌것)은 아래처럼 서비스 계층에서 모든것을 처리하는게 좋다함.
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch  orderSearch, Model model) {
        // ModelAttribute  같은건 sprint mvc의 기초니까 그걸 봐라/


        // 아래와 같이 단순 위임이면, 강사는 repository를 직접호출해서 쓰는 편이라고 함.
        // 일단은 서비스의 위임 method 그냥 씀
        List<Order> orders = orderService.findOrders(orderSearch);

        // findOrders같은경우  orderSearch의 값이 null인 경우 처리를 위해서 고생했엇다.


        model.addAttribute("orders", orders);
        return "order/orderList";
    }


    @PostMapping(value = "/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

}
