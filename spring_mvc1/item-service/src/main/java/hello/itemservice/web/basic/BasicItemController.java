package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // get방식일땐 form을 보여주기만 함
    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    // @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        //--- ModelAttribute가 해주는 일 (아예 똑같다고 함)
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        //----------
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    /**
     * @ModelAttribute("item") Item item
     * model.addAttribute("item", item); 자동 추가
     */
    // @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        //model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute("item",item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name(key)은 클래스명 첫글자만 소문자로 등록 Item -> item
     * */
    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     * */
    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    // form 확인할때 씀
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; // path variable을 스프링이 쓸수 있게 해줌
                                                // 이건.. view템플릿 처리되지는 않을듯.. 약간 responseBody같이 처리되는 느낌같긴한데..
                                                // 물론 body에 값이 들어가는게 아니라. 응답 헤더만 세팅되서 나가겠지만..
    }

    /**
     * 테스트용 데이터 추가
     * bean 생명주기 참조
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }

}
