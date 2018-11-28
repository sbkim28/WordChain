package com.ignited.wordchain.play;


import com.ignited.wordchain.play.env.DefaultKeywordAt;
import com.ignited.wordchain.play.env.KeywordAt;
import com.ignited.wordchain.util.KoreanUtil;

import java.util.*;
import java.util.function.Predicate;

public class WordSetAnalyzer {



    public static Map<Character, TableRow> createWordMap(Map<Character, TableRow> map){
        return createWordMap(map, new DefaultKeywordAt());
    }

    public static Map<Character, TableRow> createWordMap(Map<Character, TableRow> map, KeywordAt at){
        boolean update;
        do{
            update = false;
            for(Character tableKey : map.keySet()) {
                TableRow tr = map.get(tableKey);
                int containsM = 0;

                for (String word : tr.words.keySet()) {
                    int eKills = map.get(at.keywordSet(word)).killingIndex;
                    if(eKills == 0){
                        containsM = -1;
                    }else if (tr.words.get(word) == 0 || tr.words.get(word) != eKills) {
                        tr.words.put(word, eKills);
                        update = true;
                    }

                    if(eKills % 2 == 0) {
                        if(containsM != -1) containsM = Math.max(containsM, eKills);
                    }else {
                        containsM = -1;
                        if (tr.killingIndex == 0 || tr.getKillingIndex() > eKills) {
                            tr.killingIndex = eKills + 1;
                        }
                    }
                }

                if(containsM != -1 && tr.killingIndex != containsM + 1){
                    tr.killingIndex = containsM + 1;
                    update = true;
                }

            }
        }while (update);

        return map;
    }

    public static void initMap(Map<Character, TableRow> map, Collection<String> words, boolean rot, Predicate<String> predicate, KeywordAt at){
        for (String word : words){
            if(predicate!= null && !predicate.test(word)) continue;
            char get = at.keywordGet(word);
            char set = at.keywordSet(word);

            if(!map.containsKey(get)){
                map.put(get, new TableRow());
            }
            if(!map.containsKey(set)){
                map.put(set, new TableRow());
            }
            map.get(get).words.put(word, 0);
        }
        if(rot){
            for(Character key : map.keySet()){
                char rotKey = KoreanUtil.ruleOfThumb(key);
                if(rotKey != key && map.containsKey(rotKey)){
                    map.get(key).words.putAll(map.get(rotKey).words);
                }
            }
        }
    }

    public static void initMap(Map<Character, TableRow> map, Collection<String> words, boolean rot){
        initMap(map, words, rot, null, new DefaultKeywordAt());
    }

    public static void rate(Map<Character, TableRow> map){

    }

    public static class TableRow {
        private int killingIndex;
        private Map<String, Integer> words;

        public TableRow() {
            this.killingIndex = 0;
            words = new HashMap<>();
        }

        public TableRow(int killingIndex, Map<String, Integer> words) {
            this.killingIndex = killingIndex;
            this.words = words;
        }

        public int getKillingIndex() {
            return killingIndex;
        }

        public Map<String, Integer> getWords() {
            return words;
        }

        @Override
        public String toString() {
            return "TableRow{" +
                    "killingIndex=" + killingIndex +
                    ", words=" + words +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableRow tableRow = (TableRow) o;
            return killingIndex == tableRow.killingIndex &&
                    Objects.equals(words, tableRow.words);
        }

        @Override
        public int hashCode() {
            return Objects.hash(killingIndex, words);
        }


    }

}
