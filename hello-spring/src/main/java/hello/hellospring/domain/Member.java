package hello.hellospring.domain;

import javax.persistence.*;

// 회원 객체
@Entity
public class Member { // jpa가 관리하는 entity가 됨, Entity 어노테이션 붙였으니

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 알아서 생성해주는 값, 이걸 아이덴티티 전략이라고함. 오라클은 시퀀스를 쓰기도 한다함
    private Long id;

    // @Column(name = "username") => 만약 db 컬럼명이 username인거랑 mapping해주려면 이렇게 하고, 아니면 그냥 둬도 됨 . 이름이 같으니..
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
