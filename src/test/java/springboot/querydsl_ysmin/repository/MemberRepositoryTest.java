package springboot.querydsl_ysmin.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springboot.querydsl_ysmin.domain.Member;
import springboot.querydsl_ysmin.domain.Team;
import springboot.querydsl_ysmin.dto.MemberSearchCondition;
import springboot.querydsl_ysmin.dto.MemberTeamDto;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() throws Exception{
        //given
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        List<Member> result = memberJpaRepository.findByUsername("member1");
        Assertions.assertThat(result).containsExactly(member);

        //when

        //then
    }

    @Test
    public void searchTetst() throws Exception{
        //given
        Team teamA = new Team("teamA"); // 매개변수 teamA에 팀 객체를 생성하여 팀명을 teamA로 생성
        Team teamB = new Team("teamB"); // 매개변수 teamB에 팀 객체를 생성하여 팀명을 teamB로 생성
        em.persist(teamA); // 매개변수 teamA를 영속화
        em.persist(teamB); // 매개변수 teamB를 영속화

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(condition);

        // username이 정확하게 "member4" 가 맞냐
        Assertions.assertThat(result).extracting("username").containsExactly("member4");



        //when


        //then
    }

}