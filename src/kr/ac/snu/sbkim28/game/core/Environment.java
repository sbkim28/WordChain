package kr.ac.snu.sbkim28.game.core;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.HashSet;
import java.util.Set;

public class Environment {
    private static final int NUM_ROUNDS = 3;
    private static final int MS_TIMEOUT = 3000;

    private int currentPlayerIndex = 0;
    private final Player[] players;
    private Set<String> wordSet;

    private char currentCharacter = '\0';
    private String playerWord;

    public Environment(Player player1, Player player2) {
        players = new Player[2];
        wordSet = new HashSet<>();
        players[0] = player1;
        players[1] = player2;
    }

    public void setWordSet(Set<String> wordSet) {
        this.wordSet = wordSet;
    }

    public void runGame() {
        for (int i = 0; i < NUM_ROUNDS; ++i) {
            runRound();
        }

        for (int i = 0; i < 2; ++i) {
            players[i].notifyGameOver();
        }
    }

    private void runRound() {
        while(true) {
            Player player = players[currentPlayerIndex];

            char subCharacter = KoreanUtils.getSubChar(currentCharacter);
            player.notifyTurn(currentCharacter, subCharacter);

            playerWord = null;

            Thread turn = new Thread(() -> {
                String word;

                while (true) {
                    word = player.getWord();
                    if (word == null) break;
                    if (!word.isEmpty()) {
                        char c = word.charAt(0);
                        if (wordSet.contains(word) && (currentCharacter == '\0' || c == currentCharacter || c == subCharacter))
                            break;
                    }
                    player.notifySuccess(false);
                }

                playerWord = word;
                player.notifySuccess(true);
            });

            turn.start();
            try {
                turn.join(MS_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(playerWord == null) {
                // Current player lost
                return;
            }

            currentCharacter = playerWord.charAt(playerWord.length() - 1);
            currentPlayerIndex ^= 1;
        }
    }
}
