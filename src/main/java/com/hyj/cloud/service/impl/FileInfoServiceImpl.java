package com.hyj.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.cloud.annotation.ResponseResult;
import com.hyj.cloud.config.AppConfig;
import com.hyj.cloud.mapper.FileInfoMapper;
import com.hyj.cloud.mapper.UserMapper;
import com.hyj.cloud.model.dto.PageDTO;
import com.hyj.cloud.model.po.FileInfo;
import com.hyj.cloud.model.po.User;
import com.hyj.cloud.model.query.FileQuery;
import com.hyj.cloud.model.query.PageQuery;
import com.hyj.cloud.model.vo.FileVO;
import com.hyj.cloud.pojo.BizException;
import com.hyj.cloud.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.cloud.service.UserService;
import com.hyj.cloud.utils.StringTools;
import com.hyj.cloud.utils.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cxk
 * @since 2024-07-06
 */
@Service
@Slf4j
@ResponseResult
@AllArgsConstructor
public class  FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

    private final AppConfig appConfig;

    private final UserService userService;
    private final FileInfoMapper fileInfoMapper;
    @Override
    public void uploadFile(MultipartFile file) {
        try{
            String userId = UserContext.getUser();
            String md5 = getMd5(file);
            List<FileInfo> list = list(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getId, md5).eq(FileInfo::getUserId,userId));
            FileInfo fileInfo = new FileInfo();
            fileInfo.setCreateTime(LocalDateTime.now());

            //服务器有文件
            if(list.size()>0){
                fileInfo = new FileInfo();
                BeanUtils.copyProperties(list.get(0),fileInfo);;
                fileInfo.setCreateTime(LocalDateTime.now());
                updateById(fileInfo);
            }else{
                fileInfo.setFilename(file.getOriginalFilename());
                String fileExtension = fileInfo.getFilename().substring(fileInfo.getFilename().lastIndexOf(".") + 1);
                int type = 0;
                if(fileExtension.equals("pdf"))
                    type = 1;
                else if(fileExtension.equals("docx"))
                    type=2;
                else if(fileExtension.equals("xlsx"))
                    type=3;
                else if(fileExtension.equals("doc"))
                    type=4;
                fileInfo.setFileType(type);
                fileInfo.setFileSize(file.getSize());
                fileInfo.setUserId(userId);
                //服务器没此文件
                String fileRealName = md5;
                //目标目录
                String targetFolderName = appConfig.getProjectFolder();
                String month = fileInfo.getCreateTime().format(DateTimeFormatter.ofPattern("yyyyMM"));
                File targetFolder = new File(targetFolderName + "/" + month+'/'+userId);
                if (!targetFolder.exists()) {
                    targetFolder.mkdirs();
                }
                String fileSuffix = StringTools.getFileSuffix(fileInfo.getFilename());
                String targetFilePath =targetFolder.getPath()+'/'+md5+fileSuffix;
                fileInfo.setFilePath(targetFilePath);
                fileInfo.setId(md5);
                save(fileInfo);
                File dest = new File(targetFilePath);
                file.transferTo(dest);
            }
        }catch (IOException e){
            e.printStackTrace();
            throw new BizException("文件上传失败");
        }
    }

    @Override
    public PageDTO<FileInfo> getFilePage(FileQuery query) {
        String userId = UserContext.getUser();
        Page<FileInfo> page;
        page = page(query.toMpPageDefaultSortByCreateTimeDesc(),new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getUserId, userId).like(StringUtils.isNotBlank(query.getSearchKey()),FileInfo::getFilename, query.getSearchKey()));

        return PageDTO.of(page,page.getRecords());
    }

    @Override
    public PageDTO<FileVO> getAllFileList(FileQuery query) {
        String userId = UserContext.getUser();
        boolean isAdmin = userService.isAdmin(userId);
        if(!isAdmin)
            throw new BizException("没有权限");
        Page<FileVO> page = fileInfoMapper.getFileVOList(query.toMpPageDefaultSortByCreateTimeDesc(),query.getSearchKey());
        return PageDTO.of(page,page.getRecords());
    }

    @Override
    public String getFilePath(String userId, String fileId) {
        String _userId = UserContext.getUser();
        boolean isAdmin = userService.isAdmin(_userId);
        FileInfo fileInfo;
        if(!isAdmin){
            fileInfo = this.getOne(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getId, fileId).eq(FileInfo::getUserId, userId));
            if (null == fileInfo) {
                return "";
            }
        }else{
            fileInfo = this.getOne(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getId, fileId));
            if (null == fileInfo) {
                return "";
            }
        }


        return fileInfo.getFilePath();
    }

    @Override
    public void deleteFile(String userId, String fileId) {
        String _userId = UserContext.getUser();
        boolean isAdmin = userService.isAdmin(_userId);
        FileInfo fileInfo;
        if(!isAdmin)
            fileInfo = this.getOne(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getId, fileId).eq(FileInfo::getUserId, userId));
        else
            fileInfo = this.getOne(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getId, fileId));
        if (null == fileInfo) {
            throw new BizException("文件不存在");
        }
        String filePath = fileInfo.getFilePath();
        if(StringUtils.isEmpty(filePath)){
            throw new BizException("文件不存在");
        }
        Path path = Paths.get(filePath);
        try {
            // 检查文件是否存在
            if (Files.exists(path)) {
                // 删除文件
                Files.delete(path);

                log.info("删除文件成功:{}",filePath);
            } else {
                log.error("文件不存在：{}",filePath);
            }
            this.remove(new LambdaQueryWrapper<FileInfo>().eq(FileInfo::getId, fileId));
        } catch (IOException e) {
            log.error("删除文件失败：{}",filePath);
            throw new BizException("删除文件失败");
        }
    }

    /**
     * 获取上传文件的md5
     *
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (InputStream is = file.getInputStream()) {
                byte[] buffer = new byte[1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    md.update(buffer, 0, read);
                }
            }

            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Failed to calculate MD5", e);
        }
    }

}
