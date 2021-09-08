package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // JpaRepository<Member, Long> 에서 T, R중 R 인 Long은 pk type을 말함.


    @Override
    Optional<Member> findByName(String name);
}
