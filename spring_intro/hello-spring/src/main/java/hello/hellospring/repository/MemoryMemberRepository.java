package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    // 실무에서는 동시성 문제 때문에 concurrent hashmap씀, 공유 변수 니까. (static)
    // TODO: 이거.. 9.5 ..
    private static Map<Long, Member> store = new HashMap<>();
    // 이것도 실무에서느 atomLong... 씀.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // java 실무에선 list 많이 쓴다.
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
