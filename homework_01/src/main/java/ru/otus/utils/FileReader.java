package ru.otus.utils;

import java.io.InputStream;

public class FileReader {

    public InputStream read(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream(fileName);
    }
}
