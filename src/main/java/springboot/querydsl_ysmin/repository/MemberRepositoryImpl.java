package springboot.querydsl_ysmin.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import springboot.querydsl_ysmin.domain.Member;
import springboot.querydsl_ysmin.domain.QMember;
import springboot.querydsl_ysmin.domain.QTeam;
import springboot.querydsl_ysmin.dto.MemberSearchCondition;
import springboot.querydsl_ysmin.dto.MemberTeamDto;
import springboot.querydsl_ysmin.dto.QMemberTeamDto;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

public class MemberRepositoryImpl implements MemberRepositoryCustom{


    private final JPAQueryFactory queryFactory;

//    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
//        this.queryFactory = queryFactory;
//    }
    /**
     *      위 아래 같음
     */

    public MemberRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
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

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults(); // 콘텐츠 쿼리, 카운트 쿼리 두번날림

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // 콘텐츠 쿼리, 카운트 쿼리 두번날림

           long total = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(
                usernameEq(condition.getUsername()),
                teamNameEq(condition.getTeamName()),
                ageGoe(condition.getAgeGoe()),
                ageLoe(condition.getAgeLoe())
            )
                .fetchCount(); // 데이터가 몇천건 있을때 사용


        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 페이지 시작이면서 컨텐츠 사이즈보다 작을때
     * 마지막 페이지 일 때(offset + 컨텐츠 사이즈를 더해서 전체 사이즈 구함)
     */
    @Override
    public Page<MemberTeamDto> searchPageComplex_v2(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory
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
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch(); // 콘텐츠 쿼리, 카운트 쿼리 두번날림

        JPAQuery<Member> countQuery = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe())
                );


        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchCount());
//                                                                      countQuery::fetchCount
    }


    private com.querydsl.core.types.dsl.BooleanExpression usernameEq(String username) {
        return hasText(username) ? QMember.member.username.eq(username) : null;
    }

    private com.querydsl.core.types.dsl.BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? QTeam.team.name.eq(teamName) : null;
    }

    private com.querydsl.core.types.dsl.BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe != null ? QMember.member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe != null ? QMember.member.age.loe(ageLoe) : null;

    }
}
