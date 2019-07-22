package com.xueyuan.eduvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xueyuan.educommon.exception.ServiceException;
import com.xueyuan.eduvod.service.VideoService;
import com.xueyuan.eduvod.utils.AliyunVodSDKUtils;
import com.xueyuan.eduvod.utils.VodUploadUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    //上传视频
    @Override
    public String uploadVideo(MultipartFile file) {

        try {

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //获取标题
            String title = fileName.substring(0, fileName.lastIndexOf("."));//从后往前查询到最后一个.进行截取
            //fileName.lastIndexOf(".")：获取从最后到"."的索引值
            //获取输入流
            InputStream inputStream = file.getInputStream();

            //创建请求request
            UploadStreamRequest request = new UploadStreamRequest(VodUploadUtil.KEY_ID
                    , VodUploadUtil.KEY_SECRET
                    , title, fileName, inputStream);

            //获取视频点播实现类
            UploadVideoImpl uploadVideo = new UploadVideoImpl();

            UploadStreamResponse response = uploadVideo.uploadStream(request);

            //获取视频ID
            String videoId = response.getVideoId();

            System.out.println("RequestId=" + response.getRequestId() + "\n");//请求参数id

            if (!response.isSuccess()){
                String errorMessage = "视频上传错误：code：" + response.getCode() + "message：" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)){
                    throw new ServiceException(20001,errorMessage);
                }
            }

            return videoId;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(20001, "guli vod 服务上传失败");
        }


    }

    //删除视频
    @Override
    public void deleteVideo(String videoId) {

        try {
            //1 初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(VodUploadUtil.KEY_ID, VodUploadUtil.KEY_SECRET);

            //2 创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //3 传入视频id，支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoId);

            //4 调用方法实现删除
            DeleteVideoResponse response = client.getAcsResponse(request);

            System.out.println(response.getRequestId());

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void deleteVideoList(List<String> ids) {


        try {
            //1.初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(VodUploadUtil.KEY_ID, VodUploadUtil.KEY_SECRET);

            //2.创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //3.设置视频id
            //使用apache下的commons包下的StringUtils来进行字符串拼接
            String join = org.apache.commons.lang.StringUtils.join(ids.toArray(), ",");
            request.setVideoIds(join);

            //4.获取响应
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.println("response: "+response.getRequestId());

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public String getPlayAuto(String videoId) {



        try {
            //1.获取httpClient
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(VodUploadUtil.KEY_ID, VodUploadUtil.KEY_SECRET);

            //2.创建request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //设置视频Id
            request.setVideoId(videoId);

            //3.获取响应对象
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //4.获取播放凭证
            String playAuth = response.getPlayAuth();
            return playAuth;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
