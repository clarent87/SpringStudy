package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    // 이건 application.properties를 보고 spring이 bean으로 만들어서 들고 있음
    private final DataSource dataSource;

    // autowired 필요. 주입 받아야 해서
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository()); // 설명이 애매모호한데.. Bean에 등록되는거 객체 아님? method가 등록?
    }

    @Bean
    public MemberRepository memberRepository() {

//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}
