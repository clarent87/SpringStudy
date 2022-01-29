package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // ---- 아래 코드는 jpa 정석 코드인데, 실제 spring을 쓰면 대부분의 코드는 생략가능.

        // persistence-unit 에 적은 이름을 넣어줌
        // 이건 딱하나 만들어야 한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // transaction할때마다 이거 꼭만들어야한다. -> conn얻고 tr날리고 close하고.. 의 자동 같은 느낌
        EntityManager em = emf.createEntityManager();

        // jpa에서 data변경 작업은 transaction안에서 이루어져야함. --> 중요 개념
        EntityTransaction tx = em.getTransaction();


        try {
            tx.begin();
            Member member = new Member();
            member.setId(1L);
            member.setName("hello");
            em.persist(member);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            // em이 conn을 가지고 있어서 이걸 반드시 닫아 줘야한다.
            em.close();
        }

        emf.close();
    }
}
