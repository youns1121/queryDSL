package springboot.querydsl_ysmin;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class QueryDslYsminApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryDslYsminApplication.class, args);
    }


    /**
     *  JPAQueryFactory 스프링 빈으로 등록 하기
     *  동시성 문제 없음
     */

    @Bean
    JPAQueryFactory jpaQueryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }
}
