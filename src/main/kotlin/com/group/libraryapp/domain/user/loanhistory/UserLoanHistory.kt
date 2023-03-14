package com.group.libraryapp.domain.user.loanhistory

import com.group.libraryapp.domain.user.User
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne


@Entity
class UserLoanHistory (

        @ManyToOne
        val user: User,

        // val : 불변
        val bookName: String,

        // var : 가변
        var isReturn: Boolean,


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
){

    fun doReturn (){
        this.isReturn = true
    }
}