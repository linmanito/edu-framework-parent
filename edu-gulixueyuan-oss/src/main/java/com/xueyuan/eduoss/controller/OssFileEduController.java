package com.xueyuan.eduoss.controller;


import com.xueyuan.educommon.entity.R;
import com.xueyuan.eduoss.service.OssFileService;
import com.xueyuan.eduoss.utils.OssFileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/edu-file") //地址前缀
@Api(description = "文件上传")
@CrossOrigin
public class OssFileEduController {

    @Autowired
    private OssFileService ossFileService;


    @ApiOperation(value = "传入文件上传")
    @PostMapping("upload")
    public R upload(MultipartFile file, @RequestParam String host){//这个参数是传入文件的参数,参数名要与前段参数名一致

        if (!StringUtils.isEmpty(host)){//判断传过来的文件名前缀
            OssFileUtil.FILE_HOST = host;
        }

        String url = ossFileService.ossFileUpload(file);

        return R.ok().data("url" ,url);
    }


}
