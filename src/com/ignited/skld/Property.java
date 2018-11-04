package com.ignited.skld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * The Word Property
 *
 * @author Ignited
 */
public class Property{

    private final String wordClass;
    private final String[] attributes;
    private final String[] meanings;

    private Property(Builder builder) {
        this.wordClass = builder.wordClass;
        this.attributes = builder.attributes.toArray(new String[0]);
        this.meanings = builder.meanings.toArray(new String[0]);
    }

    /**
     * Gets word class.
     *
     * @return the word class
     */
    public String getWordClass() {
        return wordClass;
    }

    /**
     * Gets the array of attributes.
     *
     * @return the array of attributes
     */
    public String[] getAttributes() {
        return attributes;
    }
    /**
     * Gets the array of meanings.
     *
     * @return the array of meanings
     */
    public String[] getMeanings() {
        return meanings;
    }

    @Override
    public String toString() {
        return "Property{" +
                "wordClass='" + wordClass + '\'' +
                ", attributes=" + Arrays.toString(attributes) +
                ", meanings=" + Arrays.toString(meanings) +
                '}';
    }


    /**
     * The Property Builder.
     * Instantiates a new Property
     */
    public static class Builder{
        private String wordClass;
        private final List<String> attributes;
        private final List<String> meanings;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
            attributes = new ArrayList<>();
            meanings = new ArrayList<>();
        }

        /**
         * Set word class
         *
         * @param wordClass the word class
         * @return this
         */
        public Builder setWordClass(String wordClass){
            this.wordClass = wordClass;
            return this;
        }

        /**
         * Add an attribute of the word
         *
         * @param attribute an attribute of the word
         * @return this
         */
        public Builder addAttribute(String attribute){
            this.attributes.add(attribute);
            return this;
        }

        /**
         * Add a collection of attributes of the word
         *
         * @param attributes the attributes
         * @return this
         */
        public Builder addAllAtrribute(Collection<String> attributes){
            this.attributes.addAll(attributes);
            return this;
        }

        /**
         * Add a meaning of the word.
         *
         * @param meaning a meaning of the word
         * @return this
         */
        public Builder addMeaning(String meaning){
            meanings.add(meaning);
            return this;
        }

        /**
         * Build the property of the word.
         *
         * @return the property of the word
         */
        public Property build(){
            return new Property(this);
        }
    }
}