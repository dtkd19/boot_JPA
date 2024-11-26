package com.ezen.boot_JPA.repository;

import com.ezen.boot_JPA.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepository {
    // type, keyword , pageable 값을 주고, Page<Board> list 리턴 받는 메서드 생성
    Page<Board> serachBoards(String type, String keyword, Pageable pageable);
}
