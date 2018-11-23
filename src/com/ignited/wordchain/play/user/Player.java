package com.ignited.wordchain.play.user;

public abstract class Player {

    private String name;
    private boolean printSubmit;
    public Player(String name) {
        this.name = name;
        printSubmit = true;
    }

    public Player(String name, boolean printSubmit) {
        this.name = name;
        this.printSubmit = printSubmit;
    }

    public abstract String submitWord();

    public String getName() {
        return name;
    }

    public boolean isPrintSubmit() {
        return printSubmit;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
