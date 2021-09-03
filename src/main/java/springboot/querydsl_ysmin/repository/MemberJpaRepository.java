package springboot.querydsl_ysmin.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Fetchable;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import springboot.querydsl_ysmin.domain.Member;
import springboot.querydsl_ysmin.domain.QMember;
import springboot.querydsl_ysmin.domain.QTeam;
import springboot.querydsl_ysmin.dto.MemberSearchCondition;
import springboot.querydsl_ysmin.dto.MemberTeamDto;
import springboot.querydsl_ysmin.dto.QMemberTeamDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.reflections.util.Utils.isEmpty;
import static org.springframework.util.StringUtils.hasText;


@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    public void save(Member member) {
        em.persist(member);
    }
    public Optional<Member> findById(Long id) {
        Member findMember = em.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findAll_Querydsl(){
        return queryFactory
                .selectFrom(QMember.member)
                .fetch();
    }
    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_Querydsl(String username){
        return queryFactory
                .selectFrom(QMember.member)
                .fetch();
    }

    // Dto로 조회, Builder 사용
    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition){

        BooleanBuilder builder = new BooleanBuilder();

        if (hasText(condition.getUsername())) {
            builder.and(QMember.member.username.eq(condition.getUsername()));
        }
        if (hasText(condition.getUsername())) {
            builder.and(QTeam.team.name.eq(condition.getTeamName()));
        }

        if (condition.getAgeGoe() != null) {
            builder.and(QMember.member.age.goe(condition.getAgeGoe()));
        }

        if (condition.getAgeLoe() != null) {
            builder.and(QMember.member.age.loe(condition.getAgeLoe()));
        }


        return queryFactory
                .select(new QMemberTeamDto(
                        QMember.member.id.as("memberId"),
                        QMember.member.username,
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                ))
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(builder)
                .fetch();
    }

    public List<MemberTeamDto> search(MemberSearchCondition condition){

        return queryFactory
                .select(new QMemberTeamDto(
                        QMember.member.id.as("memberId"),
                        QMember.member.username,
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                ))
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                )
                .fetch();
    }

    /**
     * where 절 파라미터는 재 사용이 가능함 , where 절을 기본으로 사용하자
     *
     */

    //예시
    private BooleanExpression ageBetween(int ageLoe, int ageGoe){
        return ageGoe(ageGoe).and(ageGoe(ageGoe));
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? QMember.member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? QTeam.team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? QMember.member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? QMember.member.age.loe(ageLoe) : null;

    }

}
