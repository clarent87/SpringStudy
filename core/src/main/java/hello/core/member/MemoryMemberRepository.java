package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); // 사실은 concurrentHashMap을 쓰는게 좋다. 동시성 이슈 때문.

    @Override
    public void save(Member member) {
        // 실제로는 오류 처리랑 다 들어가 있어야함. (ex, data가 null이면? )
        // 예제라 단순하게 함
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
