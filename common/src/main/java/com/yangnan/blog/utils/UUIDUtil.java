package com.yangnan.blog.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String generateUUidString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
