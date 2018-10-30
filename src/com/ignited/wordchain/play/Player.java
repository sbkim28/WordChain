package com.ignited.wordchain.play;

public abstract class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public abstract String submitWord(String... chainKey);

    public String getName() {
        return name;
    }

    public void log(String... chainKey){
        System.out.print(getName() + ":");
        if(chainKey[1].isEmpty()){
            System.out.print(chainKey[0]);
        } else {
            System.out.print(chainKey[0] + "(" + chainKey[1] + ")");
        }
        System.out.print("> ");
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
