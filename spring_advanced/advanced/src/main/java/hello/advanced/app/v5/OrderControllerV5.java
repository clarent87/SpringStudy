package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceCallback;
import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;


    // [*] 생성자가 하나일땐 Autowired 생략가능
    // [*] 빈 생성자 주입. 따라서 LogTrace가 주입됨.
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        // 매번 생성해서 호출되도 되는데, 그건 부담되니까. 이렇게 만들어둠
        // 아니면 TraceTemplate을 만들어서 bean등록해둬도 됨. Config이용하던지, 걍 TraceTemplate에 @Component 붙여도 잘될듯
        // 근데 그러면 test할때 골치아픈 부분이 있다고함. 그래서 이방식을 더선호.
        // ex: OrderControllerV5 test시 LogTrace만 mocking하고 싶을때. TraceTemplate을 빈등록했다면 골치아픔..
        this.template = new TraceTemplate(trace);
    }

    // TODO: 제네릭 타입 추론. 확인 필요
    @GetMapping("/v5/request")
    public String request(String itemId) {
        return template.execute("OrderController.request()", new TraceCallback<>() {
            @Override
            public String call() { // 아마 T가 String으로 추론되어서 이렇게 된듯.
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }
}
