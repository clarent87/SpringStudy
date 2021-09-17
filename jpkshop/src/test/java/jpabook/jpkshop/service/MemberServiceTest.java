package jpabook.jpkshop.service;

import jpabook.jpkshop.domain.Member;
import jpabook.jpkshop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        // em.flush(); // Transactional 안에서 나가는 insert 문 보고 싶을때 이거 씀. Rollback은 여전히 진행됨
        assertEquals(member, memberRepository.findOne(saveId));

        // Transactional때문에 같은 영속성 context안에서는 하나의 member객체가 관리되어서 위 test가 가능하다고 함.

    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        // 이거 대신 @Test(expected = IllegalStateException.class) 이거 쓰면된다.
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다.
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        fail("예외가 발생해야 한다.");
    }

}