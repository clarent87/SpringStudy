package jpabook.jpkshop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    //이거 값 type이라고 함 
    
    private String city;
    private String street;
    private String zipcode;

    // jpa 스펙상 protected까지는 사용하는게 가능하고, 내부적으로 리플렉션, 프록시등을 쓰므로 default method는 필요
    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
