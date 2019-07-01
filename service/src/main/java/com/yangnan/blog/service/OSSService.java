package com.yangnan.blog.service;

import com.yangnan.blog.response.OSSResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OSSService {

    OSSResponse updateFile(MultipartFile file, String otherParams);

    OSSResponse updateFiles(List<MultipartFile> files, String otherParams);

    String getContentByDownloadUrl(String downloadUrl);

}
