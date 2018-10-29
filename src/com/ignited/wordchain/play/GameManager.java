package com.ignited.wordchain.play;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class GameManager {

    private final String NAME = "System";

    private final List<String> wordList;
    private final List<Player> players;

    private final List<String> used;
    private int turn;
    private String chainKey;

    public GameManager(List<String> wordList) {
        this(wordList, new ArrayList<>());
    }

    public GameManager(List<String> wordList, List<Player> players) {
        this.wordList = wordList;
        this.players = players;
        chainKey = "";
        used = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }


    public void play(){
        boolean flag = true;
        while (flag){
            flag = playGames();
        }
        chainKey = "";
    }

    private boolean playGames(){
        int i = getTurn();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String str = players.get(i).submitWord(chainKey);
            if (str.equalsIgnoreCase("gg") || str.equals("ㅎㅎ")) {
                return false;
            }
            if (!validate(str)) continue;

            if (chainKey.isEmpty() || str.startsWith(chainKey)) {
                chainKey = str.substring(str.length() - 1, str.length());
                used.add(str);
            } else {
                System.out.println(addName("첫글자가 일치하지 않습니다"));
                continue;
            }
            return true;
        }
    }

    private boolean validate(String word){
        boolean flag = false;
        if(word.length() < 2){
            System.out.println(addName("단어의 길이가 너무 짧습니다."));
        } else if (!Pattern.matches("^[ㄱ-ㅎ가-힣]*$", word)){
            System.out.println(addName("올바르지 않은 문자입니다."));
        } else if( !wordList.contains(word)){
            System.out.println(addName("사전에 없는 단어입니다."));
        } else if (used.contains(word)){
            System.out.println(addName("이미 사용한 단어입니다"));
        }else {
            flag = true;
        }

        return flag;
    }

    private String addName(String str)
    {
        return NAME + ":" + str;
    }
    private int getTurn(){
        return turn++ % players.size();
    }


    List<String> getWordList() {
        return Collections.unmodifiableList(wordList);
    }

    List<String> getUsed() {
        return Collections.unmodifiableList(used);
    }
}
