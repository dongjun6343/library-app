package com.group.libraryapp.domain.book

import com.group.libraryapp.dto.book.response.BookStatResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

// 코틀린에서는 상속받는것을 : 로 한다.
interface BookRepository : JpaRepository<Book, Long>{

    fun findByName(bookName: String): Book?
    
    // 쿼리에서 카운트 결과는 Long이기 때문에 BookStatResponse에서 count타입을 Long으로 변경
    // => QueryDSL로 리팩토링해보자.
    @Query("SELECT NEW com.group.libraryapp.dto.book.response.BookStatResponse(b.type, COUNT(b.id)) FROM Book b GROUP BY b.type")
    fun getStats(): List<BookStatResponse>
}