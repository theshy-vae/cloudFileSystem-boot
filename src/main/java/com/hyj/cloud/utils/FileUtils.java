package com.hyj.cloud.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class FileUtils {
    /**
     * 写文件到response
     *
     * @param response response
     * @param filePath 文件路径
     */
    public static void readFile(HttpServletResponse response, String filePath) {
        if (!StringUtils.hasLength(filePath)) {
            return;
        }
        if (filePath.contains("../") || filePath.contains("..\\")) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        FileInputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            byte[] byteData = new byte[(int)file.length()];
            out = response.getOutputStream();
            int len = in.read(byteData);
            out.write(byteData, 0, len);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("IO异常", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("IO异常", e);
                }
            }
        }
    }
}
