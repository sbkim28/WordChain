package com.ignited.wordchain.play.env;

public class DefaultKeywordAt implements KeywordAt {
    @Override
    public char keywordSet(String word) {
        return word.charAt(word.length() - 1);
    }

    @Override
    public char keywordGet(String word) {
        return word.charAt(0);
    }
}
