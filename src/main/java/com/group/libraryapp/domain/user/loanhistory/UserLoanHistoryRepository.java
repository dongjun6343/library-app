package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanHistoryRepository extends JpaRepository<JavaUserLoanHistory, Long> {

  JavaUserLoanHistory findByBookNameAndIsReturn(String bookName, boolean isReturn);

}
