package com.ezen.boot_JPA.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class File extends TimeBase {
    @Id
    private String uuid;
    @Column(name = "save_dir", nullable = false)
    private String saveDir;
    @Column(name= "file_name" , nullable = false)
    private String fileName;
    @Column(name = "file_type",nullable = false, columnDefinition = "integer default 0")
    private int fileType;
    private long bno;
    @Column(name = "file_size")
    private long fileSize;

}
