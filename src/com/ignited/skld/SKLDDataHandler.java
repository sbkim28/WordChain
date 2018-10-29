package com.ignited.skld;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SKLDDataHandler {

    public static void save(String path, List<KoreanWord> wordList) throws IOException {
        File file = new File(path);
        Writer writer = new FileWriter(file);
        Gson gson = new GsonBuilder().create();
        gson.toJson(wordList, writer);
        writer.close();
    }

    public static List<KoreanWord> load(String path) throws FileNotFoundException {
        File file = new File(path);
        Reader reader = new FileReader(file);
        Gson gson = new GsonBuilder().create();
        KoreanWord[] words = gson.fromJson(reader, KoreanWord[].class);
        return new ArrayList<>(Arrays.asList(words));
    }

    public static List<KoreanWord> load(InputStream is) {

        Gson gson = new GsonBuilder().create();
        KoreanWord[] words = gson.fromJson(new InputStreamReader(is), KoreanWord[].class);
        return new ArrayList<>(Arrays.asList(words));
    }

}
