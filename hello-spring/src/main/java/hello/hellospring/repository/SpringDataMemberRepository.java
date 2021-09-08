package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 이거 이렇게만 해주면 구현체는 알아서 spring-data-jpa가 만들어서 spring bean에 등록 해준다함
public interface SpringDataMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // JpaRepository<Member, Long> 에서 T, R중 R 인 Long은 pk type을 말함.


    // 아래 method 구현은
    // jpql로 select m from Member m where m.name = ? 를 이용해서 만들어짐. 
    // 이떄 method 이름이 중요! 잘만들어 줘야함
    @Override
    Optional<Member> findByName(String name); // 이것만 있으면됨.. 나머지는  JpaRepository에서 기본 method로 제공됨.
                                                // 즉. findByName은 기본 method제공이 안되서 넣은것..

}
