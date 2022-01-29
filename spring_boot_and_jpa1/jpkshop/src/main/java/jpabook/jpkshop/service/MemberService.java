package jpabook.jpkshop.service;


import jpabook.jpkshop.domain.Member;
import jpabook.jpkshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // jpa쓸때 이거 꼭필요, data 변경은 transactional 안에서 이루어져야 하기 때문. lazy loading도 가능
@RequiredArgsConstructor // final 변수만 constuctor로 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자 하나일땐 spring이 자동 인젝션
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional // 이건 읽기 전용이 아님
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId(); // member에 id가 있음이 보장이 됨 기술적으로.. 자세한건 save함수 참조

    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다. ");
        }

        // 실무에서는 WAS가 여러개 뜨므로(멀티쓰레딩 같이. .) 
        // validateDuplicateMember 메소드로는 멀티쓰레딩 환경 대응이 안됨
        // 즉 동시에 validateDuplicateMember를 타고 save가 호출됨.
        // 즉 동시에 같은 이름의 Member가 저장될수 있다.
        // 그래서 db의 user_name 필드는 unique제약조건으로 걸어서 같은 이름의 member가 저장되지 않게 한번더 방어해야한다. 
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // @Transactional(readOnly = true)
    // > 읽기 전용으로  db에서 data를 읽음, 이거 쓰면 성능의 이점이 있고,, 뭔가 여러가지 특징이 있ㅇ음. 18:27
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
