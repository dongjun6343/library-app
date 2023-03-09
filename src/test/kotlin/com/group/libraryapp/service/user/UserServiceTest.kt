package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class UserServiceTest @Autowired constructor(
    // 의존성 주입
    // UserRepository, userService에 빈이 들어옴
    // 각가의 Autowired를 constructor로 생략 가능하다
    private val userRepository: UserRepository,
    private val userService: UserService,
){

    @Test
    fun saveUserTest(){
        // given
        val request = UserCreateRequest("박동준", null)

        // when
        userService.saveUser(request);

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("박동준")
        assertThat(results[0].age).isNull()
        // age => getAge();
        // 코틀린 입장에서는 null이 아닐거라고 생각하고 값을 가져온다. (플랫폼 타입)
        // 코틀린에서는 다른 프로그래밍 언어에서 넘어온 타입들을 플랫폼 타입이라고 부른다.
        // => @Nullable 추가해서 null이 올 수 있다는 것을 코틀린한테 알려준다.
    }
}