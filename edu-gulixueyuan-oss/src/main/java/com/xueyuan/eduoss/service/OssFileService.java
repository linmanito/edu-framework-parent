package com.xueyuan.eduoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssFileService {
    String ossFileUpload(MultipartFile file);
}
