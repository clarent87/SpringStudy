package jpabook.jpkshop.domain.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 그냥두면 Book으로 기본으로 들어감
@Getter @Setter
public class Book extends Item{

    private String author;
    private String isbn;
}
