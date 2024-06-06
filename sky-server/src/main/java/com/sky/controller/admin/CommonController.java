package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * ClassName: CommonController
 * Package: com.sky.controller.admin
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/6/24 14:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api("common API")
public class CommonController {


    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/upload")
    @ApiOperation("file upload")
    public Result<String> uploadFile(MultipartFile file){
            log.info("file upload");
        try {
            String originalFilename = file.getOriginalFilename();
            String extnesion = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectname=UUID.randomUUID().toString()+extnesion;
            String path = aliOssUtil.upload(file.getBytes(), objectname);
            return Result.success(path);
        } catch (IOException e) {
           log.error("upload fail");
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
