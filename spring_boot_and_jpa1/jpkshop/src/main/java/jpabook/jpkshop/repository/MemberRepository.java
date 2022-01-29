package jpabook.jpkshop.repository;

import jpabook.jpkshop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository // 스프링 빈으로 관리 TODO:이거 어노테이션에 특징있었다고 했음. Componet + a 뭐였지?
@RequiredArgsConstructor // 생성자에 Autowired붙는데 data-jpa랑 를 쓰면 PersistenceContext로 em을 만들어주나봄.
public class MemberRepository {

    // 필드 주입
    // @PersistenceContext // 스프링이 EntityManager 를 주입해주는 annotation, 단순 Autowired로는 기본적으로 안됨
    // @Autowired // 이건 spring boot의 data-jpa랑 를 쓰면 가능.
    // private EntityManager em;

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member); // 이 순간에 영속성 context에 member를 올리는데, 이때 key, value로 올림
        // 따하서 member객체의 id를 key로 해야하기 때문에, id값을 객체에 추가해줌.
        // 즉, 아직 DB에 들어간 시점이 아니더라도 id값은 member객체에 추가됨

        // 그리고 기본적으로 persist한다고 해서 sql의 insert문이 바로 나가지는 않는다.
        // Transaction이 commit될때 나간다.
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // sql은 table을 대상으로 query를 하는데, jpql은 entity를 대상으로 query를 함.
        // jpa 기본편 참조.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
