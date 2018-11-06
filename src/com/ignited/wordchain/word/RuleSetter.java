package com.ignited.wordchain.word;


import com.ignited.skld.KoreanWord;
import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;

import java.util.*;

public class RuleSetter {

    private String name;
    private WordFilter<KoreanWord> wordFilter;

    public RuleSetter(String name) {
        this(name, words -> null);
    }

    public RuleSetter(String name, WordFilter<KoreanWord> wordFilter) {
        this.name = name;
        this.wordFilter = wordFilter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWordFilter(WordFilter<KoreanWord> wordFilter) {
        this.wordFilter = wordFilter;
    }

    public List<KoreanWord> getRawWords(){
        return SKLDDataHandler.load(DataManager.getDataAsStream(name));
    }

    public Set<String> getWords(){
        return wordFilter.filter(getRawWords());
    }

}
