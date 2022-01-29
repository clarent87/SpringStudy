package jpabook.jpkshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id; // 이거 long 대신 boxing.. (bigint에 대응함. )

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // order table에 있는 member 필드에 의해서 매핑되었다.. 라는 내용 읽기전용
    private List<Order> orders = new ArrayList<>(); // 이건 주입 받을이유가 없으니, 이렇게 직접 생성.
}
