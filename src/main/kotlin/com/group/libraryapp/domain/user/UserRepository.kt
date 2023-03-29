package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<User, Long>{

    fun findByName(name: String): User?


    //JPQL 보단 QueryDSL을 사용을 많이 함.
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userLoanHistories")
    fun findALLWithHistories(): List<User>
}