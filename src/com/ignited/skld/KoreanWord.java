package com.ignited.skld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class KoreanWord implements IWord {

    private final int homonym;
    private final String word;
    private final boolean containsOldHangeul;
    private final List<Property> properties;

    private KoreanWord(Builder builder){
        this.homonym = builder.homonym;
        this.word = builder.word;
        this.containsOldHangeul = builder.containsOldHangeul;
        this.properties = Collections.unmodifiableList(builder.properties);
    }

    public int getHomonym() {
        return homonym;
    }

    public String getWord() {
        return word;
    }

    public boolean isContainsOldHangeul() {
        return containsOldHangeul;
    }

    public List<Property> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "Word{" +
                "homonym=" + homonym +
                ", word='" + word + '\'' +
                ", containsOldHangeul=" + containsOldHangeul +
                ", properties=" +  properties +
                '}';
    }

    public static final class Builder {
        private int homonym;
        private String word;
        private boolean containsOldHangeul;
        private final List<Property> properties;

        public Builder() {
            properties = new ArrayList<>();
        }

        public Builder setHomonym(int homonym){
            this.homonym = homonym;
            return this;
        }

        public Builder setWord(String word){
            this.word = word;
            containsOldHangeul = isContainsOldHangeul(word);
            return this;
        }

        private boolean isContainsOldHangeul(String word){
            return !Pattern.matches("^[ㄱ-ㅎ가-힣]*$", word);
        }

        public Builder addProperty(Property property){
            properties.add(property);
            return this;
        }

        public KoreanWord build(){
            return new KoreanWord(this);
        }
    }


}