package com.ignited.wordchain.play.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User extends Player {


    private BufferedReader br;
    public User(String name) {
        super(name, false);
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String submitWord() {
        try {
            while (true){
                if(br.ready()){
                    return br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
