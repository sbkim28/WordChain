package com.ignited.wordchain.play.env;

public class DefaultKeywordAt implements KeywordAt {
    @Override
    public int keywordSet(String word) {
        return word.length() - 1;
    }

    @Override
    public int keywordGet(String word) {
        return 0;
    }
}
