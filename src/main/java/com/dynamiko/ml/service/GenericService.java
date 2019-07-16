package com.dynamiko.ml.service;

import com.dynamiko.ml.util.AbstractML;
import com.dynamiko.ml.util.FileObjectExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class GenericService {
    @Value("${project.groovy.base-dir}")
    private String groovyBase;

    @Value("${project.static.base-dir}")
    private String staticBase;

    public static String STATIC_BASE;

    public String doML() {
        STATIC_BASE = staticBase;
        AbstractML ml = (AbstractML) FileObjectExtractor.getGroovyObject(Paths.get(groovyBase+"/SampleML.groovy"));
        String str = ml.doML();
        return str;
    }
}
