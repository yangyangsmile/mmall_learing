package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by duanpengyang on 17-8-5.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
