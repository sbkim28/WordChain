package kr.ac.snu.sbkim28.game.core;

import kr.ac.snu.sbkim28.util.KoreanUtils;

import java.util.HashSet;
import java.util.Set;

public class ClientEnvironment implements Environment {
    private final Player[] players;

    public ClientEnvironment(Player player) {
        players = new Player[2];
        players[1] = player;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void runGame() {

    }
}
