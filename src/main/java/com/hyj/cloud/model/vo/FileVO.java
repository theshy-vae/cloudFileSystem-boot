package com.hyj.cloud.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileVO {
    private String id;
    private String filename;
    private Integer fileType;
    private String name;
    private String phone;
    private Long fileSize;
    private LocalDateTime createTime;
    private String path;
}
