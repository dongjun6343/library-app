package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.user.QUser.user
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
        //빈 주입
        private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom{
    override fun findALLWithHistories(): List<User> {
        // querydsl을 이용해서 쿼리코드 작성
        return queryFactory.select(user).distinct()
                .from(user)
                .leftJoin(userLoanHistory)
                .on(userLoanHistory.id.eq(user.id)).fetchJoin() // fetchJoin()가 없다면 N+1발생
                .fetch() // 쿼리를 실행한다.
    }
}