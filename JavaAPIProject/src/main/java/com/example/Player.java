package com.example;

import java.text.Normalizer;

public class Player {
    private String name;
    private String id;
    private String team;
    private String position;

    // player constructor
    public Player(String name, String id, String position) {
        this.name = name;
        this.id = id;
        this.position = position;
    }

    // returns the player name
    public String getName() {
        name = Normalizer.normalize(name, Normalizer.Form.NFD); // https://stackoverflow.com/questions/15190656/easy-way-to-remove-accents-from-a-unicode-string 
        return name.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    // returns the player ID
    public String getId() {
        return id;
    }

    // returns the player team
    public String getTeam() {
        return "Team: " + team;
    }

    // returns the player position
    public String getPosition() {
        return "Position: " + position;
    }

    public String toString() {
        String str = "";
        str += "Name: " + name;
        str += "\nPosition: " + getPosition();
        return str;
    }
}
