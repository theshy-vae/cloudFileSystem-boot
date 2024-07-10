package com.hyj.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.cloud.model.dto.PageDTO;
import com.hyj.cloud.model.po.FileInfo;
import com.hyj.cloud.model.query.FileQuery;
import com.hyj.cloud.model.query.PageQuery;
import com.hyj.cloud.model.vo.FileVO;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cxk
 * @since 2024-07-06
 */
public interface IFileInfoService extends IService<FileInfo> {

    public void uploadFile(MultipartFile file);

    /**
     * 获取一页文件列表
     * @param query
     * @return
     */
    public PageDTO<FileInfo> getFilePage(FileQuery query);

    public PageDTO<FileVO> getAllFileList(FileQuery query);

    /**
     * 获取文件真实路径
     * @param UserId
     * @param FileId
     * @return
     */
    public String getFilePath(String UserId,String FileId);

    /**
     * 删除文件
     * @param UserId
     * @param FileId
     */
    public void deleteFile(String UserId,String FileId);



}
