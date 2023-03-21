package com.group.libraryapp.util

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

fun fail(): Nothing{
    throw IllegalArgumentException()
}

// null인 경우 exception을 발생하는 확장함수를 만든다
// 따라서 ?를 쓰지 않는다.
fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    // this -> CrudRepository를 가리킨다.
    return this.findByIdOrNull(id) ?: fail()
}