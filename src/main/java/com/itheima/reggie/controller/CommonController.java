package com.itheima.reggie.controller;


import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("upload")
    public R<String> upload(MultipartFile file) throws IOException {
        // file 是一个临时文件，需要转存到指定位置
        log.info(file.toString());

        // get original file
        String originalFilename = file.getOriginalFilename();
        log.info(originalFilename);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // use UUID method to genetate unique file name
        UUID r = UUID.randomUUID();
        String fileName = r.toString() + suffix;

        //create directory
        File dir = new File(basePath);
        if (!dir.exists()) {
            //directory not exist, therefore created
            dir.mkdirs();
        }

        file.transferTo(new File(basePath + fileName));

        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {

        // 文件输入流
        FileInputStream fileInputStream= new FileInputStream(new File(basePath+name));


        // 文件输出流
        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        // 浏览器的图片的呈现

        int len;
        byte[] bytes= new byte[1024];
        while ((len = fileInputStream.read(bytes))!=-1){
            servletOutputStream.write(bytes,0,len);
            servletOutputStream.flush();
        }

        //关闭资源
        servletOutputStream.close();
        fileInputStream.close();

    }
}
