package com.yangnan.blog.constant;

import java.math.BigDecimal;

public interface Constant {
    /**
     * 空白显示
     */
    String SYSTEM_BLANK_SYMBOL = "-";

    /**
     * 掩码符号
     */
    String MASK_SYMBOL = "*";

    // 参数错误提示语
    String PARAM_WRONG_MSG = "参数错误！";

    interface RedisKeyPrefix {
        /**
         *
         */
        String PLUS_ONE_KEY = "plus_one_key";
    }

    interface PageSizeLimit {
        int MIN = 0;
        int MAX = 100;

    }
    interface DecimalConstants {
        BigDecimal ONE_HUNDRED_BIG_DECIMAL = new BigDecimal(100);
        BigDecimal TEN_THOUSAND_BIG_DECIMAL = new BigDecimal(10000);
        BigDecimal FIVE_THOUSAND = BigDecimal.valueOf(5000);
    }

    // OSS常量
    interface OssConstants {
        // 文件过期时间(月)
        int FILE_EXPIRES_COUNT_IN_MONTHS = 12 * 5;

        // 上传失败提示语
        String OSS_UPLOAD_ERROR_MSG = "上传失败，请重试！";

        // 下载失败提示语
        String OSS_DOWNLOAD_ERROR_MSG = "下载失败，请重试！";

    }
}
