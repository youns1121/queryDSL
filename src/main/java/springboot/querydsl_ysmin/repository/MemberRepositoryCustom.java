package springboot.querydsl_ysmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springboot.querydsl_ysmin.dto.MemberSearchCondition;
import springboot.querydsl_ysmin.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex_v2(MemberSearchCondition condition, Pageable pageable);



}
