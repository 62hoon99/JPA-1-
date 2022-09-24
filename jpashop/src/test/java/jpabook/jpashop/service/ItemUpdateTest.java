package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void updateTest () throws Exception {
        Book book = createBook("java", 10000, 10);

        //TX
        itemService.updateItem(book.getId(), "spring", 20000, 20);

        //변경감지== dirty checking//
        Item findBook = itemRepository.findOne(book.getId());

        Assertions.assertThat(findBook.getName()).isEqualTo("spring");
        Assertions.assertThat(findBook.getPrice()).isEqualTo(20000);
        Assertions.assertThat(findBook.getStockQuantity()).isEqualTo(20);
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}
