package com.uang.feipi.controller;

import com.uang.feipi.common.R;
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

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${feipi.path}")
    private String basePath;

    @PostMapping
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.getName());
        String originalFilename = file.getOriginalFilename();
        String postName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID() + postName;
        File file1 = new File(basePath);
        if (file1.exists()){
            file1.mkdirs();
        }
        file.transferTo(new File(basePath + filename));
        return R.success(filename);

    }
@GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(basePath + name);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while((len = fileInputStream.read(bytes)) != -1){
            outputStream.write(bytes, 0, len);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
        response.setContentType("image/jpg");
    }
}
