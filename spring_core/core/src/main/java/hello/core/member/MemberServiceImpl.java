package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    private MemberRepository memberRepository;

    // Autowired가 없으면 의존관계 주입이 안된다. AppConfig에선 명시가 되었지만. (new로 MemberRepository생성해서 넣는게 명시됨)
    // AutoScan을 쓰면 의존관계를 알수 없기 때문에 Autowired를 추가해야함
    @Autowired // ac.getBean(MemberRepository.class) 해서 넣는 뭐 그런 느낌 이라고 함.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
