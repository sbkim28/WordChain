package com.ignited.skld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Property{
    private final String wordClass;
    private final List<String> attributes;
    private final List<List<String>> meanings;

    private Property(Builder builder) {
        this.wordClass = builder.wordClass;
        this.attributes = Collections.unmodifiableList(builder.attributes);
        this.meanings = Collections.unmodifiableList(builder.meanings);
    }

    public String getWordClass() {
        return wordClass;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Property{" +
                "wordClass='" + wordClass + '\'' +
                ", attributes=" + attributes +
                ", meanings=" + meanings +
                '}';
    }

    public static class Builder{
        private String wordClass;
        private final List<String> attributes;
        private final List<List<String>> meanings;

        private final List<String> m;

        public Builder() {
            attributes = new ArrayList<>();
            meanings = new ArrayList<>();
            m = new ArrayList<>();
        }

        public Builder setWordClass(String wordClass){
            this.wordClass = wordClass;
            return this;
        }

        public Builder addAttribute(String attribute){
            this.attributes.add(attribute);
            return this;
        }

        public Builder addAllAtrribute(Collection<String> attributes){
            this.attributes.addAll(attributes);
            return this;
        }

        public Builder next(){
            meanings.add(Collections.unmodifiableList(new ArrayList<>(m)));
            m.clear();
            return this;
        }
        public Builder addMeaning(String meaning){
            m.add(meaning);
            return this;
        }

        public Property build(){
            return new Property(this);
        }
    }
}