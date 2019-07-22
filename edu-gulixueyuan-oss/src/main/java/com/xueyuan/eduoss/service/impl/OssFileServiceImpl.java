package com.xueyuan.eduoss.service.impl;

import com.aliyun.oss.OSSClient;
import com.xueyuan.eduoss.service.OssFileService;
import com.xueyuan.eduoss.utils.OssFileUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssFileServiceImpl implements OssFileService{


    @Override
    public String ossFileUpload(MultipartFile file) {
        String url = null;
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = OssFileUtil.END_POINT;
            String accessKeyId = OssFileUtil.ACCESS_KEY_ID;
            String accessKeySecret = OssFileUtil.ACCESS_KEY_SECRET;

            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。

            String bucketName = OssFileUtil.BUCKET_NAME;


            //文件名称
            String objectName = file.getOriginalFilename();

            //在文件名称中添加uuid，作为唯一标识
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            objectName = uuid + objectName;

            //放到以日期而建立的文件夹中/2019/07/07/01.jpg格式
            //使用转换时间格式
            String dateUrl = new DateTime().toString("yyy/MM/dd");

            //存放文件夹区别
            String fileHost = OssFileUtil.FILE_HOST;

            objectName = dateUrl + "/" + fileHost + "/" + objectName ;

            InputStream inputStream = file.getInputStream();//通过参数获取输入流
            ossClient.putObject(bucketName, objectName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();


            url = "http://" + bucketName + "." + endpoint + "/" + objectName;


        } catch (Exception e){
            e.printStackTrace();
        }

        return url;
    }
}
