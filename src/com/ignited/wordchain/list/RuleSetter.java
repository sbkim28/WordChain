package com.ignited.wordchain.list;


import com.ignited.skld.KoreanWord;
import com.ignited.skld.Property;
import com.ignited.skld.SKLDDataHandler;
import com.ignited.wordchain.data.DataManager;

import java.util.*;

public class RuleSetter {

    private List<WordClass> usableClass;

    private boolean allowNorthKorean;
    private boolean allowOldKorean;
    private boolean allowDialect;
    private boolean allowFaultyWord;

    public RuleSetter() {
        this(WordClass.NOUN);
    }

    public RuleSetter(WordClass... usableClass) {
        this(false,false,false,true,
                usableClass);
    }

    public RuleSetter(boolean allowNorthKorean, boolean allowOldKorean, boolean allowDialect, boolean allowFaultyWord, WordClass... usableClass) {
        this.usableClass = new ArrayList<>(Arrays.asList(usableClass));
        this.allowNorthKorean = allowNorthKorean;
        this.allowOldKorean = allowOldKorean;
        this.allowDialect = allowDialect;
        this.allowFaultyWord = allowFaultyWord;
    }

    public List<WordClass> getUsableClass() {
        return usableClass;
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

    public Set<String> getWords(String name){

        List<KoreanWord> kws = SKLDDataHandler.load(DataManager.getDataAsStream(name));
        Set<String> usableKws = new HashSet<>();
        for(KoreanWord kw : kws){
            String w = kw.getWord();
            if(!usableKws.contains(w) && valid(kw)){
                usableKws.add(w);
            }
        }



        return usableKws;
    }

    private boolean valid(KoreanWord kw){
        boolean ret = false;
        if(!kw.containsOldHangeul() && kw.getWord().length() >= 2){
            for(Property p : kw.getProperties()){
                boolean flag = false;
                for(String wc : p.getWordClass().split("·")){
                    if(usableClass.stream().anyMatch(wordClass -> wordClass.getWordClass().equals(wc))){
                        flag = true;
                        break;
                    }
                }
                if(!flag) continue;

                if(Arrays.stream(p.getAttributes()).anyMatch(this::invalidAttr)) continue;

                if(Arrays.stream(p.getMeanings()).anyMatch(s->{
                    // exception
                    if(s.contains("『옛」")){
                        return !allowOldKorean;
                    }
                    boolean r = false;
                    if(allowFaultyWord ||!s.contains("→")){
                        int a = s.indexOf("『");
                        int b = s.indexOf("』");
                        if(a == -1 || !invalidAttr(s.substring(a, b))){
                            r= true;
                        }
                    }
                    return r;
                })){
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    private boolean invalidAttr(String attr){
        return (!allowNorthKorean && attr.contains("북한어")) || (!allowOldKorean && attr.contains("옛말")) || (!allowDialect && attr.contains("방언"));
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
