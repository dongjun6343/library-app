package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


/**
 *  Expected size: 2 but was: 3 in: 에러발생
 *  1. 생성 테스트 시 + 1
 *  2. 조회 테스트 시 + 2
 *  두 테스트는 Spring Context를 공유한다.
 *  같은 H2 DB를 공유하기 때문에 사실은 User가 3명이 들어가있다.
 *  테스트가 끝나면 공유 자원인 DB를 깨끗하게 해주자.
 *  @AfterEach를 활용
 */

@SpringBootTest
class UserServiceTest @Autowired constructor(
    // 의존성 주입
    // UserRepository, userService에 빈이 들어옴
    // 각가의 Autowired를 constructor로 생략 가능하다
    private val userRepository: UserRepository,
    private val userService: UserService,
){
    @AfterEach
    fun clear(){
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 저장이 정상 동작한다.")
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

    @Test
    @DisplayName("유저 조회가 정상 동작한다.")
    fun getUserTest(){
        // given
        userRepository.saveAll(listOf(
                User("A", 20),
                User("B", null),
        ))

        // when
        val results = userService.getUsers()

        // then
        assertThat(results).hasSize(2)
        // ["A","B"] , findAll에 대한 검즘이므로 순서까진 체크하지 않았다.
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A","B")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20,null)

    }

    @Test
    @DisplayName("유저 업데이트가 정상 동작한다.")
    fun updateUserNameTest(){
        // given
        val saveUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(saveUser.id!!, "B")

        // when
        userService.updateUserName(request)

        // then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다.")
    fun deleteUserTest(){
        // given
        userRepository.save(User("A", null))
        // when
        userService.deleteUser("A")
        // then
        assertThat(userRepository.findAll()).isEmpty()
    }
}