package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.service.user.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
    private val userService: UserService,
) {

    @PostMapping("/user")
    fun saveUser(@RequestBody request: UserCreateRequest){
        userService.saveUser(request)
    }

    @GetMapping("/user")
    fun getUsers(): List<UserResponse>{
        return userService.getUsers()
    }

//    fun getUsers(): List<UserResponse> = userService.getUsers()

    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest){
        userService.updateUserName(request)
    }

    @DeleteMapping("/user")
    // name은 null이 아닌값으로 처리한다.
    // 삭제 시 name은 꼭 들어가야 하니깐! (선택이 아닌 필수)
    fun deleteUser(@RequestParam name: String){
        userService.deleteUserName(name)    }
}