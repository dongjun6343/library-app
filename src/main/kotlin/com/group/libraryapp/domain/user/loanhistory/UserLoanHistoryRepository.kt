package com.group.libraryapp.domain.user.loanhistory

import org.springframework.data.jpa.repository.JpaRepository


// querydsl로 리팩토링
interface UserLoanHistoryRepository : JpaRepository<UserLoanHistory, Long>