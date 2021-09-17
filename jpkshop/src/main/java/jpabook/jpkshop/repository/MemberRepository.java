package jpabook.jpkshop.repository;

import jpabook.jpkshop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository // 스프링 빈으로 관리 TODO:이거 어노테이션에 특징있었다고 했음. Componet + a 뭐였지?
public class MemberRepository {

    @PersistenceContext // 스프링이 EntityManager 를 주입해주는 annotation
    private EntityManager em;

    public void save(Member member) {
        em.persist(member); // 이 순간에 영속성 context에 member를 올리는데, 이때 key, value로 올림
                            // 따하서 member객체의 id를 key로 해야하기 때문에, id값을 객체에 추가해줌. 
                            // 즉, 아직 DB에 들어간 시점이 아니더라도 id값은 member객체에 추가됨 
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
