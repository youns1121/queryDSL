package springboot.querydsl_ysmin.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.querydsl_ysmin.dto.MemberSearchCondition;
import springboot.querydsl_ysmin.dto.MemberTeamDto;
import springboot.querydsl_ysmin.repository.MemberJpaRepository;
import springboot.querydsl_ysmin.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition){
        return memberJpaRepository.search(condition);
    }

    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCondition condition, Pageable pageable){
        return memberRepository.searchPageSimple(condition, pageable);
    }

    @GetMapping("/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCondition condition, Pageable pageable){
        return memberRepository.searchPageComplex(condition, pageable);
    }

    @GetMapping("/v4/members")
    public Page<MemberTeamDto> searchMemberV4(MemberSearchCondition condition, Pageable pageable){
        return memberRepository.searchPageComplex_v2(condition, pageable);
    }
}
