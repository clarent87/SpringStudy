package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // database와 연동이 되는 저장소면, db예외를 스프링에 맞게 고쳐주거나 등의 부가적인 기능이 좀더 제공됨
public class ItemRepository {

    // 여기는 싱글톤인데 멀티쓰레드환경에서 동시에 여기 접근하면 난리나기 때 ( concurrentHashMap써야함 )
    private static final Map<Long, Item> store = new HashMap<>(); //static 사용 ( 현업에서는 HashMap 쓰면안됨..)

    // 여기도 멀티쓰레드 환경에서 동시접근하면 문제기 때문에 AtomicLong 써야함
    private static long sequence = 0L; //static 사용


    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values()); // store보호를 위해서 직접 반환하지 않음
    }

    // 현재 updateParam에는 id field를 쓰지 않기때문에
    // 정석대로라면 id field를 뺀 ItemParameterDTO 같은걸 만들어야 한다.
    // 안그러면 개발자가 아래 method를 보고 혼란스러울수 있다 ( 왜 updateParam의 id를 안쓰지?? 같은 혼란)
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // test 용
    public void clearStore() {
        store.clear();
    }

}
