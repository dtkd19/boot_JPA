package com.ezen.boot_JPA.service;


import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public Long insert(BoardDTO boardDTO) {
        // 저장 객체는 Board
        // save() : insert 후 저장 객체의 id를 리턴
        // save() Entity 객체를 파라미터로 전송

        return boardRepository.save(convertDtoToEntity(boardDTO)).getBno();
    }

//    @Override
//    public List<BoardDTO> getList() {
//        // 컨트롤러로 보내야하는 리턴은 List<BoardDTO>
//        // DB에서 가져오는 리턴은 List<Board> -> BoardDTO 객체로 변환
//        // findAll
//        // 정렬 : Sort.by(Sort.Direction.DESC, "정렬기준 칼럼명")
//        List<Board> boardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "bno"));
// /*       List<BoardDTO> boardDTOList = new ArrayList<>();
//
//        for (Board board : boardList) {
//            boardDTOList.add(convertEntityToDto(board));
//        }*/
//
//        List<BoardDTO> boardDTOList = boardList.stream()
//                .map(b -> convertEntityToDto(b)).toList();
//
//        return boardDTOList;
//    }

    @Override
    public Page<BoardDTO> getList(int pageNo) {
        // pageNo = 0부터 시작
        // 0 => limit 0,10 , 1=> limit 10,10
        Pageable pageable = PageRequest.of(pageNo,10,Sort.by("bno").descending());
        Page<Board> list = boardRepository.findAll(pageable);

        Page<BoardDTO> boardDTOList = list.map(b -> convertEntityToDto(b));

        return boardDTOList;
    }

    @Override
    public BoardDTO getDetail(Long bno) {
        /* findById : 아이디(PK)를 주고 해당 객체를 리턴
        findById의 리턴타입 Optional<Board> 타입으로 리턴
        Optional<T> : nullPointException 발생하지 않도록 도와줌.
        Optional.isEmpty() : null일 경우 확인가능 true / false
        Optional.isPresent() : 값이 있는지 확인 true / false
        Optional.get() : 객체 가져오기
         */

        Optional<Board> optional = boardRepository.findById(bno);
        if(optional.isPresent()){
            BoardDTO boardDTO = convertEntityToDto(optional.get());
            return boardDTO;
        }

        return null;
    }

    @Override
    public Long modify(BoardDTO boardDTO) {
        // update : jpa는 업데이트가 없음
        // 기존 객체를 가져와서 set 수정 후 다시 저장

        Optional<Board> optional = boardRepository.findById(boardDTO.getBno());

        if(optional.isPresent()){
            Board entity = optional.get();
            entity.setTitle(boardDTO.getTitle());
            entity.setContent(boardDTO.getContent());
            return  boardRepository.save(entity).getBno();
        }
        return null;
    }

    // 삭제 : deleteById(id)
    @Override
    public void delete(Long bno) {
        boardRepository.deleteById(bno);
    }



}

