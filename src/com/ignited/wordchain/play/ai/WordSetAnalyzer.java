package com.ignited.wordchain.play.ai;


import com.ignited.wordchain.util.KoreanUtil;

import java.util.*;

public class WordSetAnalyzer {

    public static Map<String, TableRow> analyze (Collection<String> words){
        Map<String, TableRow> wordMap = new HashMap<>();
        initMap(wordMap, words, 1);
        analyze(wordMap);
        return wordMap;
    }

    private static void analyze(Map<String, TableRow> wordMap){
        boolean update;
        do {
            update = false;
            for (String tkey : wordMap.keySet()) {
                TableRow tr = wordMap.get(tkey);
                update = setDead(wordMap, tr, null) || update;

                if (tr.value == 0 && !tr.words.values().contains(0)) {
                    tr.value = Collections.max(tr.words.values()) + 1;
                    update = true;
                }
            }
        }while (update);
    }

    public static Map<String, TableRow> analyzeRuleOfThumb(Collection<String> words) {
        Map<String, TableRow> wordMap = new HashMap<>();

        initMap(wordMap, words, 0);
        for (String tkey : wordMap.keySet()){
            TableRow tr = wordMap.get(tkey);
            String rotKey = KoreanUtil.ruleOfThumb(tkey);
            if(tr.words.size() == 0 && (!wordMap.containsKey(rotKey) || wordMap.get(rotKey).words.size() == 0)){
                tr.value = 1;
            }
        }
        analyzeRuleOfThumb(wordMap);
        return wordMap;
    }

    private static void analyzeRuleOfThumb(Map<String, TableRow> wordMap){
        boolean update;
        do {
            update = false;
            for (String tkey : wordMap.keySet()) {
                TableRow tr = wordMap.get(tkey);
                update = setDead(wordMap, tr, tkey) || update;

                Collection<Integer> trValue = tr.words.values();

                if (tr.value == 0 && !trValue.contains(0) ) {
                    String rotKey = KoreanUtil.ruleOfThumb(tkey);
                    if(!rotKey.equals(tkey) && wordMap.containsKey(rotKey)){
                        TableRow rotTr = wordMap.get(rotKey);
                        if(rotTr.value != 0){
                            tr.value = Math.max(trValue.size() == 0 ? 0 : Collections.max(trValue) + 1,
                                    rotTr.value);
                        }
                    }else {

                        tr.value = Collections.max(tr.words.values()) + 1;
                        update = true;
                    }
                }
            }
        } while (update);
    }

    private static void initMap(Map<String, TableRow> wordMap, Collection<String> words, int def){
        for (String word : words){
            String start = word.substring(0,1);
            String end = word.substring(word.length() -1, word.length());
            if(!wordMap.containsKey(start)){
                wordMap.put(start, new TableRow(0, new HashMap<>()));
            }
            if(!wordMap.containsKey(end)){
                wordMap.put(end, new TableRow(def, new HashMap<>()));
            }
            wordMap.get(start).value = 0;
            wordMap.get(start).words.put(word, 0);
        }
    }

    private static boolean setDead(Map<String, TableRow> wordMap, TableRow tr, String tkey){
        boolean update = false;
        for (String word : tr.words.keySet()) {
            int value = wordMap.get(word.substring(word.length() - 1, word.length())).value;
            if(tr.words.get(word) == 0 && value != 0) {
                tr.words.put(word, value);
                update = true;
                if (tr.value == 0 && value % 2 == 1) {
                    tr.value = value + 1;

                    if(tkey != null) {
                        for (String rotKey : KoreanUtil.reverseRuleOfThumb(tkey)) {
                            if (tkey.equals(rotKey)) break;
                            if (wordMap.containsKey(rotKey)) {
                                TableRow rotTr = wordMap.get(rotKey);
                                if (rotTr.value == 0) rotTr.value = value + 1;
                            }
                        }
                    }
                }
            }
        }
        return update;
    }






    public static class TableRow {
        private int value;
        private Map<String, Integer> words;

        public TableRow(int value, Map<String, Integer> words) {
            this.value = value;
            this.words = words;
        }

        public int getValue() {
            return value;
        }

        public Map<String, Integer> getWords() {
            return words;
        }

        @Override
        public String toString() {
            return "TableRow{" +
                    "value=" + value +
                    ", words=" + words +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TableRow tableRow = (TableRow) o;
            return value == tableRow.value &&
                    Objects.equals(words, tableRow.words);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, words);
        }


    }

}
