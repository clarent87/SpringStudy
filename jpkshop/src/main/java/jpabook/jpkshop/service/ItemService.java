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

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item fineOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
