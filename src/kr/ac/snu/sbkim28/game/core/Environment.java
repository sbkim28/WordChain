package kr.ac.snu.sbkim28.game.core;

import kr.ac.snu.sbkim28.util.KoreanUtils;

public class Environment {
    private static final int NUM_ROUNDS = 3;
    private static final int MS_TIMEOUT = 3000;

    private int currentPlayerIndex = 0;
    private final Player[] players = new Player[2];

    private char currentCharacter = '\0';

    public Environment(Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;
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

            var wordWrapper = new Object(){ String word = null; };

            Thread turn = new Thread(() -> {
                String playerWord;

                while (true) {
                    playerWord = player.getWord();
                    char c = playerWord.charAt(0);
                    if (c == currentCharacter || c == subCharacter) break;
                    player.notifySuccessed(false);
                }

                wordWrapper.word = playerWord;
                player.notifySuccessed(true);
            });

            turn.start();
            try {
                turn.join(MS_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String word = wordWrapper.word;

            if(word == null) {
                // Current player lost
                return;
            }

            currentCharacter = word.charAt(word.length() - 1);
            currentPlayerIndex ^= 1;
        }
    }
}
