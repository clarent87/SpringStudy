package hello.servlet.domain.mamber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    private static final MemberRepository instance = new MemberRepository(); // 싱글톤
    private static final Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용 , 아마 여기에 AtomicLong을 써야 하나봄. 실무였다면

    private MemberRepository() {
    }

    public static MemberRepository getInstance() {
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 리팩터링 책에 있던 개념이었던거 같네. 저장소의 값만 내주는것.
    }

    public void clearStore() {
        store.clear();
    }

}
