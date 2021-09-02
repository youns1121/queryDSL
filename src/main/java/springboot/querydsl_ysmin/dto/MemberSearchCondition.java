package springboot.querydsl_ysmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchCondition {
    //회원명, 팀명, 나이(ageGoe, ageLoe)

    private String username;
    private String teamName;
    private Integer ageGoe; // integer 쓰는 이유 값이 null 이 가능하기 때문
    private Integer ageLoe;
}
