package jpabook.jpkshop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id; // 이거 long 대신 boxing.. (bigint에 대응함. )
    private String username;
}
