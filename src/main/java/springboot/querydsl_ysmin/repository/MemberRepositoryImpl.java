//package springboot.querydsl_ysmin.repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import javafx.beans.binding.BooleanExpression;
//import springboot.querydsl_ysmin.domain.QMember;
//import springboot.querydsl_ysmin.domain.QTeam;
//import springboot.querydsl_ysmin.dto.MemberSearchCondition;
//import springboot.querydsl_ysmin.dto.MemberTeamDto;
//import springboot.querydsl_ysmin.dto.QMemberTeamDto;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//public class MemberRepositoryImpl implements CustomMemberRepository{
//
//
//    private final JPAQueryFactory queryFactory;
//
////    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
////        this.queryFactory = queryFactory;
////    }
//    /**
//     *      위 아래 같음
//     */
//
//    public MemberRepositoryImpl(EntityManager em){
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//
//}
