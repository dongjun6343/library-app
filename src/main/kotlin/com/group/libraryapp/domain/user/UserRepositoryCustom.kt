package com.group.libraryapp.domain.user

interface UserRepositoryCustom {
    fun findALLWithHistories(): List<User>
}