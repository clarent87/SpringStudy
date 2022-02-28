package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

// 리포지토리는 인터페이스로 만드는게 좋긴한다. 구현체를 바꿀수가 있어서 (db저장, 메모리저장)
@Slf4j
@Repository
public class MemberRepository  {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={]", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {

        return findAll().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findAny();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
