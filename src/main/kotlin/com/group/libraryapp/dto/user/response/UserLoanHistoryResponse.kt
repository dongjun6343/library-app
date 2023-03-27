package com.group.libraryapp.dto.user.response

class UserLoanHistoryResponse (
    val name: String, // 유저이름
    val books: List<BookHistoryResponse>
)

data class BookHistoryResponse(
    val name: String, // 책이름
    val isReturn: Boolean,
)