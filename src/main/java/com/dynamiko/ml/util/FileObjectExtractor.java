package com.dynamiko.ml.util;

import groovy.lang.GroovyClassLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileObjectExtractor {
    private static Map<String, Long> map = new HashMap<>();
    private static Map<String, String> mapStr = new HashMap<>();
    private static Map<String, Object> mapGroovyObj = new HashMap<>();

    public static boolean isFound(Path filePath) {
        return filePath.toFile().exists();
    }

    public static boolean isUpdated(Path filePath) {
        Long stored = map.get(filePath.toFile().getName());
        Long lastModified = filePath.toFile().lastModified();
        boolean b = false;
        if (stored == null || stored.longValue() != lastModified.longValue()) {
            b = true;
        }
        return b;
    }

    public static Object getGroovyObject(Path filePath) {
        if (isUpdated(filePath)) {
            try {
                String str = getPathContent(filePath);
                Object obj = new GroovyClassLoader().parseClass(str).newInstance();
                mapGroovyObj.put(filePath.toFile().getName(), obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Object obj = mapGroovyObj.get(filePath.toFile().getName());
        return obj;
    }

    public static String getPathContent(Path filePath) {
        String str = null;
        if (isUpdated(filePath)) {
            System.out.println("UPDATED FILE **** "+filePath.toFile().getName());
            try {
                str = new String(Files.readAllBytes(filePath));
                mapStr.put(filePath.toFile().getName(), str);

                Long lastModified = filePath.toFile().lastModified();
                map.put(filePath.toFile().getName(), lastModified);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("NOT UPDATED FILE **** "+filePath.toFile().getName());
            str = mapStr.get(filePath.toFile().getName());
        }
        return str;
    }
}
