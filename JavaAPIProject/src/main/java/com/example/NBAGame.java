package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class NBAGame {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in); 
        String guess = "";
        int randomNum = 0;
        int numWins = 0;
        String ID = "";
        String statsURL = "";
        String continuePlay = "yes";

        ArrayList<String> NBA = new ArrayList<>();
        NBA = API.getAllPlayerIDs();

        
        System.out.println("\nHello! Welcome to AR's Basketball Guesser!");
        System.out.println("Try to guess the current NBA player based on the clues provided.");
        System.out.println("You'll get more information after each incorrect guess.");
        System.out.println("\nNOTE: Player teams are from the end of the 2023-2024 NBA Season (including 2024 offseason transactions and some 2024-2025 trade deadline transactions)");

        while (continuePlay.equalsIgnoreCase("yes") || continuePlay.equalsIgnoreCase("y")) {
            try {
                randomNum = API.generateRandomNum();
                ID = NBA.get(randomNum);
                
                String apiURL = "https://basketball-head.p.rapidapi.com/players/" + ID;
                String playerResponse = API.getData(apiURL);
                Player NBAPlayer = API.parsePlayer(playerResponse);
                statsURL = "https://basketball-head.p.rapidapi.com/players/" + ID + "/stats/PerGame?seasonType=Regular";
                String team = API.getPlayerTeam(ID);
                String allStars = API.getNumAllStars(ID);
                String championships = API.getNumChampionships(ID);
                String statsResponse = API.getData(statsURL);
                PlayerStats stats = API.parsePlayerSeasonStats(statsResponse);
                
                boolean correctGuess = false;
                int attempts = 0;
                int hints = 0;
                
                System.out.println("-----------------");
                System.out.println("Team: " + team);
                
                while (!correctGuess) {
                    System.out.print("\nGUESS (or type 'quit' to exit): ");
                    guess = scan.nextLine();
                    attempts++;
                    
                    if (guess.equalsIgnoreCase("quit")) {
                        continuePlay = "no";
                        System.out.println("\nGood try!");
                        System.out.println("\n" + NBAPlayer.getPosition());
                        System.out.println("Player: " + NBAPlayer.getName());
                        System.out.println("Points Per Game: " + stats.getPoints());
                        System.out.printf("Rebounds Per Game: %.1f\n", stats.getRebounds());
                        System.out.println("Assists Per Game: " + stats.getAssists());
                        System.out.println("Steals Per Game: " + stats.getSteals());
                        System.out.println("Blocks Per Game: " + stats.getBlocks());
                        System.out.println("Three Pointers Made Per Game: " + stats.getThreePointers());
                        System.out.println(allStars);
                        System.out.println(championships);
                        break;
                    }
                    
                    if (guess.equalsIgnoreCase(NBAPlayer.getName())) {
                        System.out.println("\nCorrect! Great job!");
                        System.out.println("\nPlayer: " + NBAPlayer.getName());
                        System.out.println("Points Per Game: " + stats.getPoints());
                        System.out.printf("Rebounds Per Game: %.1f\n", stats.getRebounds());
                        System.out.println("Assists Per Game: " + stats.getAssists());
                        System.out.println("Steals Per Game: " + stats.getSteals());
                        System.out.println("Blocks Per Game: " + stats.getBlocks());
                        System.out.println("Three Pointers Made Per Game: " + stats.getThreePointers());
                        System.out.println(allStars);
                        System.out.println(championships);
                        System.out.print("\nIt took you " + attempts + " attempt");
                        if (attempts != 1) {
                            System.out.print("s");
                        }
                        System.out.print(" to guess correctly! You used " + hints + " hint");
                        if (hints != 1) {
                            System.out.println("s.");
                        } else {
                            System.out.println(".");
                        }
                        correctGuess = true;
                        numWins++;
                        
                        System.out.print("\nWould you like to play again? (yes/no): ");
                        continuePlay = scan.nextLine();
                        
                        while (!continuePlay.equalsIgnoreCase("yes") && 
                               !continuePlay.equalsIgnoreCase("no") && 
                               !continuePlay.equalsIgnoreCase("y") && 
                               !continuePlay.equalsIgnoreCase("n")) {
                            System.out.print("Please enter 'yes' or 'no': ");
                            continuePlay = scan.nextLine();
                        }
                    } else {
                        System.out.println("\nIncorrect! Here's another clue:");
                        switch (attempts) { // https://www.geeksforgeeks.org/switch-statement-in-java/
                            case 1:
                                System.out.println(NBAPlayer.getPosition());
                                break;
                            case 2:
                                System.out.println("Points Per Game: " + stats.getPoints());
                                break;
                            case 3:
                                System.out.printf("Rebounds Per Game: %.1f\n", stats.getRebounds()); // utilizing a floater to avoid excessive decimals, only using it for rebounds because I manually added offensive and defensive rebounds
                                break;
                            case 4:
                                System.out.println("Assists Per Game: " + stats.getAssists());
                                break;
                            case 5:
                                System.out.println("Steals Per Game: " + stats.getSteals());
                                break;
                            case 6:
                                System.out.println("Blocks Per Game: " + stats.getBlocks());
                                break;
                            case 7:
                                System.out.println("Three Pointers Made Per Game: " + stats.getThreePointers());
                                break;
                            case 8:
                                String firstName = NBAPlayer.getName().split(" ")[0];
                                System.out.println("First Name: " + firstName);
                                break;
                            default:
                                System.out.println("Here are all the hints:");
                                System.out.println("Team: " + team);
                                System.out.println(NBAPlayer.getPosition());
                                System.out.println("Points Per Game: " + stats.getPoints());
                                System.out.printf("Rebounds Per Game: %.1f\n", stats.getRebounds());
                                System.out.println("Assists Per Game: " + stats.getAssists());
                                System.out.println("Steals Per Game: " + stats.getSteals());
                                System.out.println("Blocks Per Game: " + stats.getBlocks());
                                System.out.println("Three Pointers Made Per Game: " + stats.getThreePointers());
                                System.out.println("First Name: " + NBAPlayer.getName().split(" ")[0]);
                                break;
                        }
                        if (hints < 2) {
                        System.out.println("\nWould you like an extra hint?");
                        String hintUse = scan.nextLine();
                        if (hintUse.equalsIgnoreCase("yes")) {
                            switch (hints) {
                                case 0:
                                    System.out.println("\n" + allStars);
                                    hints++;
                                    break;
                                case 1:
                                    System.out.println("\n" + championships);
                                    hints++;
                                    break;
                                default:
                                    System.out.print("");
                            }
                        }
                    }
                }
            }
            } finally {}
        }
        System.out.print("\nYou guessed " + numWins + " player");
        if (numWins != 1) {
            System.out.print("s");
        }
        System.out.println(" correctly. Thanks for playing!");
        scan.close();
    }
}
