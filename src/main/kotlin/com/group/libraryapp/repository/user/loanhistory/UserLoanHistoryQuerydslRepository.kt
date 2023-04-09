package com.group.libraryapp.repository.user.loanhistory

import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.*
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component


@Component //빈으로 인식
class UserLoanHistoryQuerydslRepository (
        private val queryFactory: JPAQueryFactory,
){
    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
        return queryFactory.select(userLoanHistory)
                .from(userLoanHistory)
                .where(
                        userLoanHistory.bookName.eq(bookName),
                        status?.let { userLoanHistory.status.eq(status) }
                )
                .limit(1) // 하나만 가져온다.
                .fetchOne()
    }

    fun count(status: UserLoanStatus) : Long {
        return queryFactory.select(userLoanHistory.count())
                .from(userLoanHistory)
                .where(
                        userLoanHistory.status.eq(status)
                )
                .fetchOne() ?: 0L
    }
}