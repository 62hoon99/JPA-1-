package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> { //Entity 타입과 id의 타입

    //select m from Member m where m.name = ?
    List<Member> findByName(String name); //interface에 써두면 알아서 구현된다. findBy****: findBy 뒤에 붙은 것을 select 해준다.
}
