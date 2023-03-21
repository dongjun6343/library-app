package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
        private val userRepository: UserRepository,
){

    // id 'org.jetbrains.kotlin.plugin.jpa' version '1.6.21' 추가한다.
    // 그렇지 않으면 클래스와 메서드에 open 을 붙여야한다.
    @Transactional
    fun saveUser(request: UserCreateRequest){
        val user = User(request.name, request.age)
        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
                .map { user -> UserResponse(user) }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest){
        // 코틀린의 확장함수 사용해서 제어할 수 없을 거 같은 findById의 Optional 제거하기.
        //  findById -> findByIdOrNull
        // val user = userRepository.findByIdOrNull(request.id) ?: fail()
        val user = userRepository.findByIdOrThrow(request.id)
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUserName(name: String){
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }
}