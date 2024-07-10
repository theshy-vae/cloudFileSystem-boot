package com.hyj.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.cloud.model.po.FileInfo;
import com.hyj.cloud.model.vo.FileVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cxk
 * @since 2024-07-06
 */
public interface FileInfoMapper extends BaseMapper<FileInfo> {

    Page<FileVO> getFileVOList(Page<FileVO> page, @Param("searchKey") String searchKey);

}
