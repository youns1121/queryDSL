package springboot.querydsl_ysmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.querydsl_ysmin.domain.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //select m from Member m where m.username = ?
    List<Member> findByUsername(String username);
}
