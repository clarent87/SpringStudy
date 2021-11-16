package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

// db와 관련있다는것을 알려줌
@Entity
public class Member {

    // pk가 뭔지는 알려줘야함
    @Id
    private Long id;
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
