package com.yangnan.blog.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.ObjectMetadata;
import com.yangnan.blog.constant.Constant;
import com.yangnan.blog.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;

/**
 * Alibaba Cloud OSS Util
 *
 * @author anzhe
 * @date 2019-02-10
 */
@Slf4j
public class OSSUtil {

    private static final String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAI9dplqI3ye7d9";
    private static final String accessKeySecret = "izD4jt6Yl8RtprY5Omvp9uXKguELAj";
    private static final String bucketName = "yangnanblog";

    public static OSSClient client;

    static {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, accessKeySecret);
        // OSS client config
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        client = new OSSClient(endpoint, credentialsProvider, clientConfiguration);
    }

    public static String uploadFile(String originalFileName, InputStream fileStream) throws BusinessException {
        // isOrgIdValid(orgId);
        try {
            // combine oss object key
            int extensionStartIndex = originalFileName.lastIndexOf('.');
            String fileExtension = extensionStartIndex == -1 ? "" : originalFileName.substring(extensionStartIndex);
            String key = ("blog" + '/') + IDWorkerUtil.getID() + fileExtension;
            // save original file name to metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setUserMetadata(Collections.singletonMap("originalFileName", originalFileName));
            client.putObject(bucketName, key, fileStream, metadata);
            // url expires after 5 years from now
            return client.generatePresignedUrl(bucketName, key, DateUtil.offsetMonth(new Date(), Constant.OssConstants.FILE_EXPIRES_COUNT_IN_MONTHS)).toString();
        } catch (Exception e) {
            log.error("Upload file to OSS occurred an error: {}.", e);
            throw new BusinessException("Upload file to OSS occurred an error: " + e.getMessage() + ".", Constant.OssConstants.OSS_UPLOAD_ERROR_MSG);
        }
    }

    public static String downloadAsString(String downloadUrl) {
        try {
            if (StrUtil.isBlank(downloadUrl)) {
                throw new RuntimeException("Download url wrong: " + downloadUrl);
            }
            return HttpClientUtil.sendGetRequest(downloadUrl);
        } catch (Exception e) {
            log.error("Download file from OSS occurred an error: {}.", e);
            throw new BusinessException("Download file from OSS occurred an error: {}. " + e.getMessage() + ".", Constant.OssConstants.OSS_DOWNLOAD_ERROR_MSG);
        }
    }

    // validate orgId
    private static void isOrgIdValid(String orgId) throws BusinessException {
        if (StrUtil.isBlank(orgId)) {
            log.error("Upload file to oss occurred an error: orgId({}) wrong.", orgId);
            throw new BusinessException("Upload file to OSS occurred an error: orgId wrong.", Constant.OssConstants.OSS_UPLOAD_ERROR_MSG);
        }
    }
}
