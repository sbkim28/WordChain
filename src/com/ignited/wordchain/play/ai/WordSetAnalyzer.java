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
                update = setDead(wordMap,tr,null) || update;

                if (tr.killingIndex == 0 && !tr.words.values().contains(0)) {
                    tr.killingIndex = Collections.max(tr.words.values()) + 1;
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
                tr.killingIndex = 1;
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
                update = setDead(wordMap,tr,tkey) || update;

                Collection<Integer> trValue = tr.words.values();

                if (tr.killingIndex == 0 && !trValue.contains(0) ) {
                    String rotKey = KoreanUtil.ruleOfThumb(tkey);
                    if(!rotKey.equals(tkey) && wordMap.containsKey(rotKey)){
                        TableRow rotTr = wordMap.get(rotKey);
                        if(rotTr.killingIndex != 0){
                            tr.killingIndex = Math.max(trValue.size() == 0 ? 0 : Collections.max(trValue) + 1,
                                    rotTr.killingIndex);
                            update = true;
                        }
                    }else {
                        tr.killingIndex = Collections.max(tr.words.values()) + 1;
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
            wordMap.get(start).killingIndex = 0;
            wordMap.get(start).words.put(word, 0);
        }
    }

    private static boolean setDead(Map<String, TableRow> wordMap, TableRow tr, String tkey){
        boolean update = false;
        for (String word : tr.words.keySet()) {
            int killingIndex = wordMap.get(word.substring(word.length() - 1, word.length())).killingIndex;
            if((tr.words.get(word) == 0 || tr.words.get(word) != killingIndex ) && killingIndex != 0) {
                tr.words.put(word, killingIndex);
                update = true;
                if ((tr.killingIndex == 0 || killingIndex < tr.killingIndex) && killingIndex % 2 == 1) {
                    tr.killingIndex = killingIndex + 1;

                    if(tkey != null) {
                        for (String rotKey : KoreanUtil.reverseRuleOfThumb(tkey)) {
                            if (tkey.equals(rotKey)) break;
                            if (wordMap.containsKey(rotKey)) {
                                TableRow rotTr = wordMap.get(rotKey);
                                if (rotTr.killingIndex == 0 || killingIndex < rotTr.killingIndex) rotTr.killingIndex = killingIndex + 1;
                            }
                        }
                    }
                }
            }
        }
        return update;
    }


    public static Map<Character, TableRow> createWordMap(Collection<String> words){
        Map<Character, TableRow> map = new HashMap<>();
        initMap(map, words);

        boolean update;
        do{
            update = false;
            for(TableRow tr : map.values()) {
                int containsM = 0;

                for (String word : tr.words.keySet()) {

                    int eKills = map.get(word.charAt(word.length() - 1)).killingIndex;
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

    public static Map<Character, TableRow> createROTWordMap(Collection<String> words){
        Map<Character, TableRow> map = new HashMap<>();
        initMap(map, words);

        boolean update;
        do {
            update = false;
            for (Character tableKey : map.keySet()){
                int containsM = 0;

                TableRow tr = map.get(tableKey);
                for (String word : tr.words.keySet()){
                    int eKills = map.get(word.charAt(word.length() - 1)).killingIndex;
                    if(eKills == 0){
                        containsM = -1;
                    }else if(tr.words.get(word) == 0 || tr.words.get(word) != eKills){
                        tr.words.put(word, eKills);
                        update = true;

                    }

                    if(eKills % 2 == 0){
                        if(containsM != -1) containsM = Math.max(containsM, eKills);
                    }else{
                        containsM = -1;
                        if(tr.killingIndex == 0 || tr.getKillingIndex() > eKills){
                            tr.killingIndex = eKills + 1;
                            char[] rotKey = KoreanUtil.reverseRuleOfThumb(tableKey);
                            if(tableKey != rotKey[0]) {
                                rotKeyProcess(map, rotKey[0], eKills);
                                if (rotKey.length != 1) {
                                    rotKeyProcess(map, rotKey[1], eKills);
                                }
                            }
                        }
                    }
                }

                if(containsM != -1 ){
                    boolean flag = tr.killingIndex != containsM + 1;
                    char rotKey = KoreanUtil.ruleOfThumb(tableKey);
                    if(rotKey != tableKey && map.containsKey(rotKey)){
                        TableRow rotTr = map.get(rotKey);
                        if(rotTr.killingIndex % 2 == 1 && (flag || tr.killingIndex != rotTr.killingIndex)){
                            tr.killingIndex = Math.max(containsM + 1 , rotTr.killingIndex);
                        }
                    }else if(flag){
                        tr.killingIndex = containsM + 1;
                        update = true;
                    }
                }
            }

        }while (update);

        return map;
    }

    private static void rotKeyProcess(Map<Character, TableRow> map, char rotKey, int eKills){
        if (map.containsKey(rotKey)) {
            TableRow rotTr = map.get(rotKey);
            if (rotTr.killingIndex == 0 || eKills < rotTr.killingIndex){
                rotTr.killingIndex = eKills + 1;
            }
        }
    }

    private static void initMap(Map<Character, TableRow> map, Collection<String> words){
        for(String word : words){
            char start = word.charAt(0);
            char end = word.charAt(word.length() - 1);

            if(!map.containsKey(start)){
                map.put(start, new TableRow());
            }
            if(!map.containsKey(end)){
                map.put(end, new TableRow());
            }
            map.get(start).words.put(word, 0);
        }
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
