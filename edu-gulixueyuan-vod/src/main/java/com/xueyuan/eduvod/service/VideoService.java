package com.xueyuan.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String videoId);

    void deleteVideoList(List<String> ids);

    String getPlayAuto(String videoId);
}
