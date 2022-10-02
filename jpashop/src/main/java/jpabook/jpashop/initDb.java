package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/***
 * * UserA
 * JPA1
 * JPA2
 *
 * * UserB
 *  Spring1
 *  Spring2
 */
@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct // spring bean이 올라오고 나면 아래 메서드를 자동 호출해줌
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        public void dbInit1() {
            Member member = getMember("userA", new Address("서울", "1", "1111"));
            em.persist(member);

            Book book1 = getBook("JPA1 BOOK", 10000, 100);
            em.persist(book1);

            Book book2 = getBook("JPA2 BOOK", 20000, 200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = getOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = getMember("userB", new Address("경기", "22", "222"));
            em.persist(member);

            Book book1 = getBook("Spring1", 10000, 100);
            em.persist(book1);

            Book book2 = getBook("Spring2", 20000, 200);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = getOrder(member, orderItem1, orderItem2);
            em.persist(order);
        }

        private static Member getMember(String name, Address address) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(address);
            return member;
        }

        private static Order getOrder(Member member, OrderItem... orderItem) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            Order order = Order.createOrder(member, delivery, orderItem);
            return order;
        }

        private static Book getBook(String bookName, int price, int stockQuantity) {
            Book book2 = new Book();
            book2.setName(bookName);
            book2.setPrice(price);
            book2.setStockQuantity(stockQuantity);
            return book2;
        }
    }
}
