package com.group.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

// 코틀린에서는 상속받는것을 : 로 한다.
interface BookRepository : JpaRepository<Book, Long>{

    fun findByName(bookName: String): Book?
}