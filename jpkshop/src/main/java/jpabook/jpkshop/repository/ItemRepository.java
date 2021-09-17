package jpabook.jpkshop.repository;

import jpabook.jpkshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // item에 id가 없다는 것은 새로생성된 id
        // 이경우 persist을 이용해서 저장하고 id를 생성
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // id가 있다는것은, 어디 db에서 가져온것.
            // 이경우 신규 등록이 아닌 update라고 보면된다. (진짜 update는 아니지만.. )
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
