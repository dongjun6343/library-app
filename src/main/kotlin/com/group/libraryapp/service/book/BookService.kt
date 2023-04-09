package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.repository.book.BookQuerydslRepository
import com.group.libraryapp.repository.user.loanhistory.UserLoanHistoryQuerydslRepository
import com.group.libraryapp.util.fail


@Service
class BookService (
        private val bookRepository: BookRepository,
        private val userRepository: UserRepository,
        private val userLoanHistoryRepository: UserLoanHistoryRepository,
        private val bookQuerydslRepository: BookQuerydslRepository,
        private val userLoanHistoryQuerydslRepository: UserLoanHistoryQuerydslRepository,
){
    @Transactional
    fun saveBook(request: BookRequest){
        val book = Book(request.name, request.type)
        bookRepository.save(book)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest){
        val book = bookRepository.findByName(request.bookName) ?: fail()

        if(userLoanHistoryQuerydslRepository.find(request.bookName, UserLoanStatus.LOANED) != null){
            throw IllegalArgumentException("이미 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest){
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    //단순히 조회하는 API이므로 트랜잭션은 readonly처리
//    @Transactional(readOnly = true)
//    fun countLoanedBookV_1(): Int {
//        return userLoanHistoryRepository.findAllByStatus(UserLoanStatus.LOANED).size
//    }

    @Transactional(readOnly = true)
    fun countLoanedBook(): Int {
        return userLoanHistoryQuerydslRepository.count(UserLoanStatus.LOANED).toInt()
    }

//    @Transactional(readOnly = true)
//    fun getBookStatistics_V1(): List<BookStatResponse> {
//        val results = mutableListOf<BookStatResponse>()
//        // 모든 책 가져오기
//        val books = bookRepository.findAll()
//
//        for (book in books){
//            // book.type == dto.type이 같은 값.
//            val targetDto = results.firstOrNull { dto -> book.type == dto.type }
//            if(targetDto == null){
//                results.add(BookStatResponse(book.type, 1))
//            } else {
//                // 비어있지 않다면, 타켓Dto의 카운드를 올려준다.
//                targetDto.plusOne()
//            }
//        }
//        return results;
//    }

    // getBookStatistics_V1를 코틀린 특징을 이용해서 간결하게 변경
//    @Transactional(readOnly = true)
//    fun getBookStatisticsV_2(): List<BookStatResponse> {
//        val results = mutableListOf<BookStatResponse>()
//        val books = bookRepository.findAll()
//        for (book in books){
//            // ?. => 앞에 있는 값이 널이 아닌경우 실행.
//            val targetDto = results.firstOrNull { dto -> book.type == dto.type }?.plusOne()
//                    ?: results.add(BookStatResponse(book.type, 1))  // 널인 경우
//        }
//        return results;
//    }

    // group by를 써서 소스 리펙토링
//    @Transactional(readOnly = true)
//    fun getBookStatistics(): List<BookStatResponse> {
//        return bookRepository.findAll() // List<Book>
//          .groupBy { book -> book.type } //타입을 기준으로 group by,  Map<BookType, List<Book>>
//          .map { (type, books) -> BookStatResponse(type, books.size) } // type => BookType, books => List<Book>
//
//        // 최종적으로 List<BookStatResponse>
//        // 불필요했던 plusOne도 삭제할 수 있다. => 불필요했던 가변필드인 var count를 val count로 수정할 수 있다.
//    }

    @Transactional(readOnly = true)
    fun getBookStatistics(): List<BookStatResponse> {
//        return bookRepository.getStats()
        return bookQuerydslRepository.getStats()
    }
}