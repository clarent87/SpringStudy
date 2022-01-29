package jpabook.jpkshop.domain;

import jpabook.jpkshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id") // db에서 이 필드 name
    private Long id;

    private String name;

    // 중간 table 연결 세팅을 아래와 같이 함.
    @ManyToMany
    @JoinTable(name = "category_item", // join할 중간 table
            joinColumns = @JoinColumn(name = "category_id"), // 해당 table에서 FK
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 해당 table에서 FK
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // TODO: 이건 어디에 있지? -> FK 세팅한거 같다.  맞음.
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
