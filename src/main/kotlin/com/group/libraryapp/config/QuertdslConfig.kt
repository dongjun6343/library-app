package com.group.libraryapp.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager


// QueryDsl에 필요한 빈을 등록하는 과정
@Configuration
class QuertdslConfig(
   private val em: EntityManager,
) {
   @Bean
   fun querydsl(): JPAQueryFactory{
       return JPAQueryFactory(em)
   }
}