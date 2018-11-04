package com.ignited.skld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Korean Word
 *
 * @author Ignited
 */
public class KoreanWord implements IWord {

    private final int homonym;
    private final String word;
    private final boolean containsOldHangeul;
    private final Property[] properties;

    private KoreanWord(Builder builder){
        this.homonym = builder.homonym;
        this.word = builder.word;
        this.containsOldHangeul = builder.containsOldHangeul;
        this.properties = builder.properties.toArray(new Property[0]);
    }

    /**
     * Gets index of homonym.
     *
     * @return the index of homonym
     */
    public int getHomonym() {
        return homonym;
    }


    public String getWord() {
        return word;
    }

    /**
     * If the word contains old hangeul, it returns true. Else, it returns false.
     * That is, it returns whether or not the word is composed of ㄱ~ㅎ, ㅏ~ㅣ or 가~힣.
     *
     * @return whether the word contains old hangeul or not
     */
    public boolean containsOldHangeul() {
        return containsOldHangeul;
    }

    /**
     * Gets properties of the word.
     *
     * @return the properties
     * @see com.ignited.skld.Property
     */
    public Property[] getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "Word{" +
                "homonym=" + homonym +
                ", word='" + word + '\'' +
                ", containsOldHangeul=" + containsOldHangeul +
                ", properties=" + Arrays.toString(properties) +
                '}';
    }

    /**
     * The Korean World Builder
     * Instantiates a new KoreanWord
     */
    public static final class Builder {
        private int homonym;
        private String word;
        private boolean containsOldHangeul;
        private final List<Property> properties;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            properties = new ArrayList<>();
        }

        /**
         * Set the index of homonym
         *
         * @param homonym the homonym
         * @return this
         */
        public Builder setHomonym(int homonym){
            this.homonym = homonym;
            return this;
        }

        /**
         * Set the Korean Word.
         * Also checks if the word contains old Hangeul.
         *
         * @param word the word
         * @return this
         */
        public Builder setWord(String word){
            this.word = word;
            containsOldHangeul = containsOldHangeul(word);
            return this;
        }

        private boolean containsOldHangeul(String word){
            return !Pattern.matches("^[ㄱ-ㅎㅏ-ㅣ가-힣]*$", word);
        }

        /**
         * Add the property of the word
         *
         * @param property the property
         * @return this
         */
        public Builder addProperty(Property property){
            properties.add(property);
            return this;
        }

        /**
         * Build korean word.
         *
         * @return the korean word
         */
        public KoreanWord build(){
            return new KoreanWord(this);
        }
    }


}