package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    Logger log = LoggerFactory.getLogger(getClass()); // 변수명은 logger라고도 쓰는데 요새는 그냥 log라고 쓴다.

    /**
     * 기본 요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP 메서드 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE */
    @RequestMapping("/hello-basic") // url을 두개 이상 넣을수도 있음. 해당 url들은 전부여기에 매핑됨
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * 편리한 축약 애노테이션 (코드보기) * @GetMapping
     * @PostMapping
     * @PutMapping
     * @DeleteMapping
     * @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능 -> @PathVariable String userId 이렇게 쓸수 있다는것
     * @PathVariable("userId") String userId -> @PathVariable userId
     * */
    @GetMapping("/mapping/{userId}") // url 경로를 template화 함
    public String mappingPath(@PathVariable("userId") String data) { //PathVariable 어노테이션으로 url에 있는 변수 뽑는거 가능
        log.info("mappingPath userId={}", data);
        return "ok";
    }

    /**
     * PathVariable 사용 다중
    */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑 -> 잘쓰지는 않음
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug") // 쿼리 파라메터로 mode에 debug값이 있어야 이 method가 호출됨
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode", --> 헤더에 mode가 있어야함
     * headers="!mode"
     * headers="mode=debug"  --> 헤더에 mode가 있고 그값이 debug여야 함
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = "application/json") // -> headers를 쓰면안됨 content-type관해서는 spring에서 뭐 더해주는게 있어서
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type * produces = "text/html"
     * produces = "!text/html" * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html") // 이 매핑은 결과로 text/html을 반환한다는것을 알려주는것. accept가 json만 있었다면 406에러 반환해줌
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }

}
