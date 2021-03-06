package springboot.querydsl_ysmin.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.web.WebAppConfiguration;
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
    MemberRepository memberRepository;

    @Test
    public void basicTest() throws Exception{
        //given
        Member member = new Member("member1", 10);
        memberRepository.save(member);

        List<Member> result = memberRepository.findByUsername("member1");
        Assertions.assertThat(result).containsExactly(member);

        //when

        //then
    }

    @Test
    public void srarchPageSimple() {
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
        PageRequest pageRequest = PageRequest.of(0, 3); // 페이지가 0부터 3이냐

        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, pageRequest);

        Assertions.assertThat(result.getSize()).isEqualTo(3);
        Assertions.assertThat(result.getContent()).extracting("username").containsExactly("member1","member2","member3");




        //when


        //then
    }

}