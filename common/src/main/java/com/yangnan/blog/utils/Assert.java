package com.yangnan.blog.utils;

import com.yangnan.blog.constant.Constant;
import com.yangnan.blog.exception.BusinessException;
import com.yangnan.blog.result.MessageResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.yangnan.blog.result.MessageResult.HAS_NOT_PRIVILEGE;

public abstract class Assert {

    protected Assert() {
    }

    public static void allNonNull(String message, Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new BusinessException(message);
            }
        }
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object obj, String codeMessage, String userMessage) {
        if (obj == null) {
            throw new BusinessException(codeMessage, userMessage);
        }
    }

    public static void isTrue(Boolean obj, String message) {
        if (!obj) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(Boolean obj, String codeMessage, String userMessage) {
        if (!obj) {
            throw new BusinessException(codeMessage, userMessage);
        }
    }

    public static void isNotTrue(Boolean obj, String message) {
        if (obj) {
            throw new BusinessException(message);
        }
    }

    public static void isNotTrue(Boolean obj, MessageResult message) {
        if (obj) {
            throw new BusinessException(message);
        }
    }

    public static void notBlank(String obj, String message) {
        if (obj == null || "".equals(obj.trim())) {
            throw new BusinessException(message);
        }
    }

    public static void notBlank(String obj, String codeMsg, String userMsg) {
        if (obj == null || "".equals(obj.trim())) {
            throw new BusinessException(codeMsg, userMsg);
        }
    }

    public static void notEmpty(List<?> objList, String message) {
        if (objList == null || objList.size() == 0) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(List<?> objList, String codeMessage, String userMessage) {
        if (objList == null || objList.size() == 0) {
            throw new BusinessException(codeMessage, userMessage);
        }
    }

    public static void notEmpty(Set<?> objList, String message) {
        if (objList == null || objList.size() == 0) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Map<?, ?> objList, String message) {
        if (objList == null || objList.size() == 0) {
            throw new BusinessException(message);
        }
    }

    public static void notZero(long obj, String message) {
        if (obj == 0) {
            throw new BusinessException(message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (obj != null) {
            throw new BusinessException(message);
        }
    }

    public static void isNotNull(Object obj, String message) {
        if (obj == null) {
            throw new BusinessException(message);
        }
    }

    public static void length(String str, int minLength, int maxLength, String message) {
        if (str == null) {
            throw new BusinessException(message);
        }
        if (str.length() < minLength || str.length() > maxLength) {
            throw new BusinessException(message);
        }
    }

    public static void hasPrivilege(boolean hasRole) {
        if (!hasRole) {
            throw new BusinessException(HAS_NOT_PRIVILEGE);
        }
    }

    public static void transactionalFailed(String message) {
        throw new BusinessException(message);
    }

    public static void idWorker(String message) {
        throw new BusinessException(message);
    }

    public static void assertPageNumberAndPageSize(Integer pageNumber, Integer pageSize) throws BusinessException {

        Assert.isNotTrue(null == pageNumber || pageNumber <= 0, "pageNumber无效");

        Assert.isNotTrue(null == pageSize || pageSize <= 0 || pageSize > Constant.PageSizeLimit.MAX, "pageSize无效");
    }

}
