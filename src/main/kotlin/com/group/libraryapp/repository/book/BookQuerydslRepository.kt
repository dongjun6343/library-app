package com.group.libraryapp.repository.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component


// BookQuerydslRepository 해당 클래스를 스프링 빈으로 인식할 수 있도록
// @Component 추가
@Component
class BookQuerydslRepository (
        private val queryFactory: JPAQueryFactory,
){
    // Projections.constructor
    // 첫번째로 나오는 클래스의 생성자를 가져오겠다
    // 이떄 뒤에 나오는 파라미터들이 생성자로 들어간다.
    fun getStats() : List<BookStatResponse>{
      return queryFactory
        .select(
            Projections.constructor(
              // 반환하고 싶은 dto
              BookStatResponse::class.java,
              book.type,
              book.id.count()
            )
        )
        .from(book)
        .groupBy(book.type)
        .fetch()

    }
}