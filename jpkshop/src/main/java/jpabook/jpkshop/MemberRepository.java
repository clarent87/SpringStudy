package jpabook.jpkshop;

import jpabook.jpkshop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    // DAO랑 비슷한 거라고 함 (DAO는 db 액세스 해주는 object)
    // Entity를 찾아주는 역할? 이라고 함

    @PersistenceContext
    private EntityManager em; // EntityManager를 spring boot가 어노테이션 보고 주입 해줌.

    // 왜 Member를 넘기지 않지?
    // => 커맨드랑 쿼리를 분리해라. 원칙
    // => 8:33 save함수는 커맨드 성이라서. Member를 직접넘기지 않는다함.
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
