package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus

class UserLoanHistoryResponse (
    val name: String, // 유저이름
    val books: List<BookHistoryResponse>
){
    companion object {
        fun of(user: User): UserLoanHistoryResponse {
            return UserLoanHistoryResponse(
                    name = user.name,
                    books = user.userLoanHistories.map(BookHistoryResponse::of)
            )
        }
    }
}

data class BookHistoryResponse(
    val name: String, // 책이름
    val isReturn: Boolean,
) {
    //정적 팩토리 메서드 사용
    companion object {
        fun of(history: UserLoanHistory) : BookHistoryResponse{
            return BookHistoryResponse(
                    name = history.bookName,
                    // 로직을 UserLoanHistory단으로 내릴 수 있다.
                    //isReturn = history.status == UserLoanStatus.RETURNED
                    isReturn = history.isReturn
            )
        }
    }
}