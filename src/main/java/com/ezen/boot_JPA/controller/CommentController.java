package com.ezen.boot_JPA.controller;

import com.ezen.boot_JPA.dto.CommentDTO;
import com.ezen.boot_JPA.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment/*")
public class CommentController {
    private final CommentService commentService;


    @ResponseBody
    @PostMapping("/post")
    public String post(@RequestBody CommentDTO commentDTO){

        Long cno = commentService.post(commentDTO);

        return cno > 0 ? cno.toString() : "0";
    }

    @ResponseBody
    @GetMapping("/list/{bno}/{page}")
    public Page<CommentDTO> list(@PathVariable("bno") long bno ,@PathVariable("page") int page){
        // page = 0부터시작
        // 1page => 0 / 2page => 1
        page = (page == 0 ? 0 : page-1);

//        List<CommentDTO> list = commentService.getList(bno);

        Page<CommentDTO> list = commentService.getList(bno,page);

        log.info(">>> list >> {}" , list);

        return list;
    }

    @ResponseBody
    @PutMapping("/modify")
    public String modify(@RequestBody CommentDTO commentDTO){

        Long cno = commentService.modify(commentDTO);

        return cno > 0 ? cno.toString() : "0";
    }

    @ResponseBody
    @GetMapping("/delete/{cno}")
    public String delete(@PathVariable("cno") Long cno){

        long cnoVal = commentService.delete(cno);

        log.info(">>>> cnoVal >> {} ", cno);

        return cnoVal == 0 ? "1" : "0" ;
    }

}
