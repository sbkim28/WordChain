package com.ignited.wordchain.word;


import com.ignited.skld.KoreanWord;
import com.ignited.skld.Property;

import java.util.*;

public class KoreanWordFilter implements WordFilter<KoreanWord> {

    private List<WordClass> usableClass;

    private boolean allowNorthKorean;
    private boolean allowOldKorean;
    private boolean allowDialect;
    private boolean allowFaultyWord;

    public KoreanWordFilter() {
        this(WordClass.NOUN);
    }

    public KoreanWordFilter(WordClass... usableClass) {
        this(false,false,false,true,
                usableClass);
    }

    public KoreanWordFilter(boolean allowNorthKorean, boolean allowOldKorean, boolean allowDialect, boolean allowFaultyWord, WordClass... usableClass) {
        this.usableClass = new ArrayList<>(Arrays.asList(usableClass));
        this.allowNorthKorean = allowNorthKorean;
        this.allowOldKorean = allowOldKorean;
        this.allowDialect = allowDialect;
        this.allowFaultyWord = allowFaultyWord;
    }

    public List<WordClass> getUsableClass() {
        return usableClass;
    }

    @Override
    public Set<String> filter(List<KoreanWord> words) {
        Set<String> wordSet = new HashSet<>();
        for (KoreanWord word : words){
            if(usable(word) && validProperties(word)){
                wordSet.add(word.getWord());
            }
        }
        return wordSet;
    }

    private boolean usable(KoreanWord kw){
        return !kw.containsOldHangeul() && kw.getWord().length() >= 2;
    }

    private boolean validProperties(KoreanWord kw){
        boolean ret = false;
        for(Property p : kw.getProperties()){
            boolean flag = false;

            if(confirmWc(p.getWordClass()) && confirmMeanings(p.getMeanings())) {
                if (Arrays.stream(p.getAttributes()).noneMatch(this::invalidAttr)){
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    private boolean confirmWc(String wcs){
        for(String wc : wcs.split("·")){
            if(usableClass.stream().anyMatch(wordClass -> wordClass.getWordClass().equals(wc))){
                return true;
            }
        }
        return false;
    }

    private boolean confirmMeanings(String[] meanings){
        boolean r = false;
        for (String m : meanings){
            // exception
            if(m.contains("『옛」")){
                return !allowOldKorean;
            }
            if(allowFaultyWord ||!m.contains("→")){
                int a = m.indexOf("『");
                int b = m.indexOf("』");
                if(a == -1 || !invalidAttr(m.substring(a, b))){
                    r= true;
                    break;
                }
            }
        }
        return r;
    }

    private boolean invalidAttr(String attr){
        return (!allowNorthKorean && attr.contains("북한어")) || (!allowOldKorean && attr.contains("옛말")) || (!allowDialect && attr.contains("방언"));
    }

    public void setAllowNorthKorean(boolean allowNorthKorean) {
        this.allowNorthKorean = allowNorthKorean;
    }

    public void setAllowOldKorean(boolean allowOldKorean) {
        this.allowOldKorean = allowOldKorean;
    }

    public void setAllowDialect(boolean allowDialect) {
        this.allowDialect = allowDialect;
    }

    public void setAllowFaultyWord(boolean allowFaultyWord) {
        this.allowFaultyWord = allowFaultyWord;
    }


    public enum WordClass {
        NOUN("명사"), PRONOUN("대명사"), NUMERAL("수사"),
        BOUND_NOUN("의존명사"), NULL(""), INTERJECTION("감탄사"),
        ADVERB("부사"), DETERMINER("관형사");

        private String wordClass;

        WordClass(String wordClass) {
            this.wordClass = wordClass;
        }

        public String getWordClass() {
            return wordClass;
        }
    }
}
