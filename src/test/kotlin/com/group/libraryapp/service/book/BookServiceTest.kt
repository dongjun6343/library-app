package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.BookRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository
){

    @Test
    @DisplayName("")
    fun saveBookTest(){
        //given

        //when

        //then
    }


}