package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.group.libraryapp.dto.book.request.BookLoanRequest


@Service
class BookService (
        private val bookRepository: BookRepository,
        private val userRepository: UserRepository,
        private val UserLoanHistoryRepository: UserLoanHistoryRepository
){
    @Transactional
    fun saveBook(request: BookRequest){
        val book = Book(request.name)
        bookRepository.save(book)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest){
        val book = bookRepository.findByName(request.name)
              .orElseThrow(::IllegalArgumentException)

        if(UserLoanHistoryRepository.findByBookNameAndIsReturn(request.name, false) != null){
            throw IllegalArgumentException("이미 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.name).orElseThrow(::IllegalArgumentException)
        user.loanBook(request.bookName)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest){
        val user = userRepository.findByName(request.name).orElseThrow(::IllegalArgumentException)
        user.returnBook(request.bookName)
    }
}