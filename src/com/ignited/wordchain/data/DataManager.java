package com.ignited.wordchain.data;

import java.io.InputStream;
import java.net.URL;

public class DataManager {

    public static URL getData(String name){
        return DataManager.class.getResource(name);
    }

    public static InputStream getDataAsStream(String name) {
        return DataManager.class.getResourceAsStream(name);
    }
}
