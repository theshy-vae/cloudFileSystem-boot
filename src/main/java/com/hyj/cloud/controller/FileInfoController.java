package com.hyj.cloud.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyj.cloud.annotation.ResponseResult;
import com.hyj.cloud.config.AppConfig;
import com.hyj.cloud.model.dto.PageDTO;
import com.hyj.cloud.model.po.FileInfo;
import com.hyj.cloud.model.po.User;
import com.hyj.cloud.model.query.FileQuery;
import com.hyj.cloud.model.query.PageQuery;
import com.hyj.cloud.model.vo.FileVO;
import com.hyj.cloud.pojo.BizException;
import com.hyj.cloud.service.IFileInfoService;
import com.hyj.cloud.service.UserService;
import com.hyj.cloud.service.impl.FileInfoServiceImpl;
import com.hyj.cloud.utils.FileUtils;
import com.hyj.cloud.utils.StringTools;
import com.hyj.cloud.utils.UserContext;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cxk
 * @since 2024-07-06
 */
@RestController
@RequestMapping("/file")
@ResponseResult
@AllArgsConstructor
public class FileInfoController {

    private final IFileInfoService fileInfoService;

    @RequestMapping("/uploadFile")
    public void uploadFile(@RequestParam("file") MultipartFile file){
        fileInfoService.uploadFile(file);
    }

    @PostMapping("/getFileList")
    public PageDTO<FileInfo> getFilePage(@RequestBody FileQuery query){
        return fileInfoService.getFilePage(query);
    }

    @PostMapping("/getAllFileList")
    public PageDTO<FileVO> getAllFileList(@RequestBody FileQuery query){
        return fileInfoService.getAllFileList(query);
    }

    @GetMapping("/getFileStream/{fileId}")
    public void getFileStream(@PathVariable("fileId") String fileId, HttpServletResponse response){
        String userId = UserContext.getUser();
        String filePath = fileInfoService.getFilePath(userId,fileId);
        if(StringUtils.isNotEmpty(filePath)){
            FileUtils.readFile(response, filePath);
        }
    }

    @GetMapping("/deleteFile/{fileId}")
    public void deleteFile(@PathVariable("fileId") String fileId){
        String userId = UserContext.getUser();
        fileInfoService.deleteFile(userId,fileId);
    }

}
