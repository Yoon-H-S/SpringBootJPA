package jpabook.jpashop.repository;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) { // 아이디 값이 없다면 새로운 상품을 생성하는 것.
            em.persist(item);
        } else { // 아이디 값이 있다면 이미 등록된 상품.
            em.merge(item); // update 비슷한거.
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
