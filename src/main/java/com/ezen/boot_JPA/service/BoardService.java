package com.ezen.boot_JPA.service;

import com.ezen.boot_JPA.dto.BoardDTO;
import com.ezen.boot_JPA.dto.BoardFileDTO;
import com.ezen.boot_JPA.dto.FileDTO;
import com.ezen.boot_JPA.entity.Board;
import com.ezen.boot_JPA.entity.File;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {
    // 추상 메서드만 가능한 인터페이스
    // 메서드가 default(접근제한자) 구현 가능.

    // BoardDTO(class) : bno title writer content regAt modAt
    // Board(table) : bno title writer content
    // BoardDTO => board 변환
    // 화면에서 가져온 BoardDTO 객체를 저장을 위한 Board 객체로 변환
    default Board convertDtoToEntity(BoardDTO boardDTO){

        return Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .writer(boardDTO.getWriter())
                .content(boardDTO.getContent())
                .build();
    }


    // board => BoardDTO 변환
    // DB에서 가져온 Board 객체를 화면에 뿌리기위한 BoardDTO 객체로 변환
    default  BoardDTO convertEntityToDto(Board board){

        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .writer(board.getWriter())
                .content(board.getContent())
                .regAt(board.getRegAt())
                .modAt(board.getModAt())
                .build();
    }

    default File convertDtoToEntity(FileDTO fileDTO){

        return File.builder()
                .uuid(fileDTO.getUuid())
                .saveDir(fileDTO.getSaveDir())
                .fileName(fileDTO.getFileName())
                .fileType(fileDTO.getFileType())
                .bno(fileDTO.getBno())
                .fileSize(fileDTO.getFileSize())
                .build();


    }

    default FileDTO convertEntityToDto(File file){

        return  FileDTO.builder()
                .uuid(file.getUuid())
                .saveDir(file.getSaveDir())
                .fileName(file.getFileName())
                .fileType(file.getFileType())
                .bno(file.getBno())
                .fileSize(file.getFileSize())
                .regAt(file.getRegAt())
                .modAt(file.getModAt())
                .build();
    }

    Long insert(BoardDTO boardDTO);

//    List<BoardDTO> getList();

    BoardFileDTO getDetail(Long bno);

//    Long modify(BoardDTO boardDTO);

    void delete(Long bno);

    Page<BoardDTO> getList(int pageNo, String type, String keyword);

    Long insert(BoardFileDTO boardFileDTO);

    long deleteFile(String uuid);

    Long modify(BoardFileDTO boardFileDTO);

    FileDTO getFile(String uuid);
}
