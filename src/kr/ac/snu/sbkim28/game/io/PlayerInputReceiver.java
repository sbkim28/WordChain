package kr.ac.snu.sbkim28.game.io;

public interface PlayerInputReceiver {
    String waitUntilPlayerInput();
    void haltWaiting();
}
