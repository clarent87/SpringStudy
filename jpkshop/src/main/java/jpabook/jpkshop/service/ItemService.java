package jpabook.jpkshop.service;

import jpabook.jpkshop.domain.item.Item;
import jpabook.jpkshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    // 보면 ItemService는 ItemRepository에 위임만하는 class

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, Item item) {
        Item findItem = itemRepository.findOne(itemId);
        
        // data변경시 이렇게 set method로 깔면안된다. 
        // 클린코드 6장내용인데 set대신 change 같은 의미있는 method를 만들어서 값을 변경해야함
        findItem.setPrice(item.getPrice());
        findItem.setName(item.getName());
        findItem.setStockQuantity(item.getStockQuantity());
        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
