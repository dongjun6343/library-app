package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book (
    val name: String,

    // @Enumerated(EnumType.STRING) : 0,1,2.. 숫자로 DB에 저장하는 값을 String으로 바꾼다.
    @Enumerated(EnumType.STRING)
    val type: BookType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
){
        // 초기화 블럭
    init {
        if(name.isBlank()){
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    
    // 테스트 코드만을 위한 함수 생성
    // 오브젝트
    companion object {
        fun fixture(
          name: String = "책 이름",
          type: BookType = BookType.COMPUTER,
          id: Long? = null,
        ): Book {
          return Book(
              name = name,
              type = type,
              id = id,
          )
        }
    }
}