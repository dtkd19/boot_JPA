package com.ezen.boot_JPA.dto;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentDTO {
    private Long cno;
    private Long bno;
    private String writer;
    private String content;
    private LocalDateTime regAt;
    private LocalDateTime modAt;
}
