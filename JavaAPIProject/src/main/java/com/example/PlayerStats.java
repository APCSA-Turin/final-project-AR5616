package com.example;

public class PlayerStats {
    private double pointsPerGame;
    private double reboundsPerGame;
    private double assistsPerGame;
    private double turnoversPerGame;
    private double stealsPerGame;
    private double blocksPerGame;
    private double fieldGoalsAttempted;
    private double threePointersMade;
    private double freeThrowsAttempted;

    // player stats constructor
    public PlayerStats(double points, double rebounds, double assists, double turnovers, double steals, double blocks, double fieldGoals, double threePointers, double freeThrows) {
        pointsPerGame = points;
        reboundsPerGame = rebounds;
        assistsPerGame = assists;
        turnoversPerGame = turnovers;
        stealsPerGame = steals;
        blocksPerGame = blocks;
        fieldGoalsAttempted = fieldGoals;
        threePointersMade = threePointers;
        freeThrowsAttempted = freeThrows;
    }

    // returns player points per game
    public double getPoints() {
        return pointsPerGame;
    }

    // returns player rebounds per game
    public double getRebounds() {
        return reboundsPerGame;
    }

    // returns player assists per game
    public double getAssists() {
        return assistsPerGame;
    }

    // returns player turnovers per game
    public double getTurnovers() {
        return turnoversPerGame;
    }

    // returns player steals per game
    public double getSteals() {
        return stealsPerGame;
    }

    // returns player blocks per game
    public double getBlocks() {
        return blocksPerGame;
    }

    // returns player field goals per game
    public double getFieldGoalsAttempted() {
        return fieldGoalsAttempted;
    }

    // returns player three pointers per game
    public double getThreePointers() {
        return threePointersMade;
    }

    // returns player free throws per game
    public double getFreeThrowsAttempted() {
        return freeThrowsAttempted;
    }

    public String toString() {
        String str = "";
        str += "Points Per Game: " + getPoints();
        str += "\nRebounds Per Game: " + getRebounds();
        str += "\nAssists Per Game: " + getAssists();
        str += "\nSteals Per Game: " + getSteals();
        str += "\nBlocks Per Game: " + getBlocks();
        str += "\nTurnovers Per Game: " + getTurnovers();
        str += "\n3-Pointers Per Game: " + getThreePointers();
        return str; 
    }
}
