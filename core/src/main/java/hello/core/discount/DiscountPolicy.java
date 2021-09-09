package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    /**
     * @param member 는 등급을 보고 itemPrice에 할인률 곱해서 할인액을 반환해 주려고..
     * @param price 는 itemPrice
     * @return 할인 대상 금액 ( 즉 할인액 )
     */
    int discount(Member member, int price);
}
