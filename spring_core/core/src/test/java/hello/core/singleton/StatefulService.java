package hello.core.singleton;

public class StatefulService {
    // 실무에서 자주 발생하는 문제.
    // test code랑 함꼐 봐야함
    
    private int price; //상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; //여기가 문제!

        // 실제로 멀티 쓰레드였다.면 여기서  getPrice() 하고 뭐 그럴때 문제가 생긴다함..
        // 물론 price는 다른 쓰레드에서 수정한 상태.. 이경우 debugging하기 매우 힘들듯..
    }

    public int getPrice() {
        return price;
    }
}
