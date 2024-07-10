package com.hyj.cloud.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cxk
 * @since 2024-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("file_info")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户id
     */
    private String userId;


    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件分类：1：pdf 2:doc 3:excel
     */
    private Integer fileType;

    private LocalDateTime createTime;


}
