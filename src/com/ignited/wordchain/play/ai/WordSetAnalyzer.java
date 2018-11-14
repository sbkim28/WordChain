package com.ignited.wordchain.play.ai;


import com.ignited.wordchain.util.KoreanUtil;

import java.util.*;

public class WordSetAnalyzer {

    public static Map<Character, TableRow> createWordMap(Collection<String> words){
        Map<Character, TableRow> map = new HashMap<>();
        initMap(map, words);

        boolean update;
        do{
            update = false;
            for(Character tableKey : map.keySet()) {
                TableRow tr = map.get(tableKey);
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
