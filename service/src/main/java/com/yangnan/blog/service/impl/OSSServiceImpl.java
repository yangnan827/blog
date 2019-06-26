package com.yangnan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.yangnan.blog.OSSRequest;
import com.yangnan.blog.OSSResponse;
import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.service.OSSService;
import com.yangnan.blog.utils.OSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.yangnan.blog.constant.Constant.OssConstants.OSS_UPLOAD_ERROR_MSG;

@Service
@Slf4j
public class OSSServiceImpl implements OSSService {

    @Override
    public OSSResponse updateFile(MultipartFile file, String otherParams) {
        if (file == null) {
            throw new BusinessException(OSS_UPLOAD_ERROR_MSG);
        }

        OSSRequest ossRequest = JSON.parseObject(otherParams, OSSRequest.class);

        // 上传文件并获取下载链接
        String downloadUrl;
        try (InputStream inputStream = file.getInputStream()) {
            downloadUrl = OSSUtil.uploadFile(file.getOriginalFilename(), inputStream);
        } catch (IOException e) {
            log.error("Upload file to OSS occurred an error: {}.", e);
            throw new BusinessException(e.getMessage(), OSS_UPLOAD_ERROR_MSG);
        }
        return OSSResponse.builder().downloadUrl(downloadUrl).params(ossRequest.getParams()).build();
    }

    @Override
    public OSSResponse updateFiles(List<MultipartFile> files, String otherParams) {
        if (files.isEmpty() || files.stream().anyMatch(Objects::isNull)) {
            throw new BusinessException(OSS_UPLOAD_ERROR_MSG);
        }

        OSSRequest ossRequest = JSON.parseObject(otherParams, OSSRequest.class);

        List<String> downloadUrls = new ArrayList<>();
        // 上传文件并获取下载链接
        for (MultipartFile file : files) {
            String downloadUrl;
            try (InputStream inputStream = file.getInputStream()) {
                downloadUrl = OSSUtil.uploadFile(file.getOriginalFilename(), inputStream);
            } catch (IOException e) {
                log.error("Upload file to OSS occurred an error: {}.", e);
                throw new BusinessException(e.getMessage(), OSS_UPLOAD_ERROR_MSG);
            }
            downloadUrls.add(downloadUrl);
        }

        return OSSResponse.builder().downloadUrls(downloadUrls).params(ossRequest.getParams()).build();
    }

    @Override
    public String getContentByDownloadUrl(String downloadUrl) {
        return null;
    }

}
