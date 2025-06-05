package com.example;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.management.RuntimeErrorException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class API {
    public static String getData(String endpoint) throws Exception {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-RapidAPI-Host", "basketball-head.p.rapidapi.com");
            connection.setRequestProperty("X-RapidAPI-Key", "2a2c6959b4mshcb410ea89214935p12b3bdjsnc9ac97ec7362");

            int responseCode = connection.getResponseCode();

            BufferedReader buff = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = buff.readLine()) != null) {
                content.append(inputLine);
            }
            buff.close();
            connection.disconnect();
            return content.toString();
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                throw new FileNotFoundException("Player not found");
            }
            throw e;
        }
    }

    // getting the players current team (the API gives a list of teams played on, this method returns the current/latest team)
    public static String getPlayerTeam(String ID) throws Exception {
        String endpoint = "https://basketball-head.p.rapidapi.com/players/" + ID;
        String jsonResponse = getData(endpoint);
    
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject body = root.getAsJsonObject("body");
    
        // getting the teams array
        JsonArray teams = body.getAsJsonArray("teams");
    
        // getting the latest team (current team)
        String currentTeam = teams.get(teams.size() - 1).getAsString();
    
        // extracting the team name
        String[] parts = currentTeam.split(",");
        String teamName = parts[0];
    
        // returning the team
        return teamName;
    }

    // returns the number of All-Star selections this player has had
    public static String getNumAllStars(String ID) throws Exception {
        String endpoint = "https://basketball-head.p.rapidapi.com/players/" + ID;
        String jsonResponse = getData(endpoint);
    
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject body = root.getAsJsonObject("body");
    
        // accessing the entire accolades array on the API
        JsonArray accolades = body.getAsJsonArray("accolades");

        // set to 0 by default
        String allStars = "0x All Star";
        if (accolades.size() == 0) {
            return allStars;
        }

        // using a for loop to get the correct index where this accolade is stationed
        for (int i = 0; i < accolades.size(); i++) {
            if (accolades.get(i).getAsString().contains("All Star")) {
                allStars = accolades.get(i).getAsString();
            }
        }
        return allStars;
    }

    // returns the number of championships this NBA player has 
    public static String getNumChampionships(String ID) throws Exception {
        String endpoint = "https://basketball-head.p.rapidapi.com/players/" + ID;
        String jsonResponse = getData(endpoint);
    
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);
        JsonObject body = root.getAsJsonObject("body");
    
        // accessing the accolades array on the API
        JsonArray accolades = body.getAsJsonArray("accolades");

        // set to 0 by default
        String championships = "0x NBA Champ";
        if (accolades.size() == 0) {
            return championships;
        }

        // utilizing a for loop to access the correct index where the accolade is stationed
        for (int i = 0; i < accolades.size(); i++) {
            if (accolades.get(i).getAsString().contains("NBA Champ")) {
                championships = accolades.get(i).getAsString();
            }
        }
        return championships;
    }

    // initializing two ArrayLists, player names and player IDs
    private static final ArrayList<String> PLAYER_NAMES;
    private static final ArrayList<String> PLAYER_IDS;

        // utilizing a static initialization block so that the IDs only have to be extracted once
        static { // https://stackoverflow.com/questions/2420389/static-initialization-blocks

        // player names is set to include 180 names, the 6 main players on each of the 30 NBA Teams
        PLAYER_NAMES = new ArrayList<>(Arrays.asList("Trae Young", "Dejounte Murray", "Saddiq Bey", "Jalen Johnson", "Vit Krejci", "Bogdan Bogdanovic", "Jayson Tatum", "Jaylen Brown", "Kristaps Porzingis", "Derrick White", "Jrue Holiday", "Sam Hauser", "Mikal Bridges", "Cameron Johnson", "Nic Claxton", "Spencer Dinwiddie", "Ben Simmons", "Dorian Finney-Smith", "LaMelo Ball", "Davis Bertans", "Kris Dunn", "Gordon Hayward", "PJ Washington", "Nick Richards", "DeMar DeRozan", "Zach LaVine", "Coby White", "Alex Caruso", "Nikola Vucevic", "Patrick Williams", "Donovan Mitchell", "Darius Garland", "Max Strus", "Georges Niang", "Jarrett Allen", "Caris LeVert", "Luka Doncic", "Kyrie Irving", "Dereck Lively", "Derrick Jones Jr.", "Daniel Gafford", "Maxi Kleber", "Jamal Murray", "Nikola Jokic", "Michael Porter Jr.", "Aaron Gordon", "Kentavious Caldwell-Pope", "Reggie Jackson", "Cade Cunningham", "Jaden Ivey", "Ausar Thompson", "Bojan Bogdanovic", "Isaiah Stewart", "Jalen Duren", "Stephen Curry", "Klay Thompson", "Draymond Green", "Andrew Wiggins", "Chris Paul", "Jonathan Kuminga", "Fred VanVleet", "Alperen Sengun", "Cason Wallace", "Dillon Brooks", "Amen Thompson", "Cam Whitmore", "Tyrese Haliburton", "Pascal Siakam", "Bennedict Mathurin", "Myles Turner", "Bruce Brown", "Buddy Hield", "James Harden", "Kawhi Leonard", "Paul George", "Russell Westbrook", "Ivica Zubac", "Norman Powell", "LeBron James", "Gabe Vincent", "DAngelo Russell", "Austin Reaves", "Rui Hachimura", "Christian Wood", "Ja Morant", "Marcus Smart", "Desmond Bane", "Jaren Jackson Jr.", "Santi Aldama", "Luke Kennard", "Jimmy Butler", "Bam Adebayo", "Tyler Herro", "Terry Rozier", "Duncan Robinson", "Kevin Love", "Damian Lillard", "Giannis Antetokounmpo", "Khris Middleton", "Brook Lopez", "Bobby Portis", "Malik Beasley", "Anthony Edwards", "Karl-Anthony Towns", "Rudy Gobert", "Luka Garza", "Mike Conley", "Naz Reid", "Zion Williamson", "Brandon Ingram", "CJ McCollum", "Jonas Valanciunas", "Herbert Jones", "Spencer Dinwiddie", "Jalen Brunson", "Julius Randle", "OG Anunoby", "RJ Barrett", "Josh Hart", "Mitchell Robinson", "Shai Gilgeous-Alexander", "Chet Holmgren", "Jalen Williams", "Josh Giddey", "Lu Dort", "Isaiah Joe", "Paolo Banchero", "Franz Wagner", "Cole Anthony", "Jalen Suggs", "LeBron James", "Joe Ingles", "Joel Embiid", "Tyrese Maxey", "Nicolas Batum", "Kelly Oubre Jr.", "Buddy Hield", "Kyle Lowry", "Kevin Durant", "Devin Booker", "Bradley Beal", "Jusuf Nurkic", "Grayson Allen", "Eric Gordon", "Anfernee Simons", "Jerami Grant", "Deandre Ayton", "Scoot Henderson", "Shaedon Sharpe", "Malcolm Brogdon", "De'Aaron Fox", "Domantas Sabonis", "Keegan Murray", "Kevin Huerter", "Malik Monk", "Harrison Barnes", "Victor Wembanyama", "Devin Vassell", "Julian Champagnie", "Jeremy Sochan", "Tre Jones", "Zach Collins", "Scottie Barnes", "Pascal Siakam", "Dennis Schroder", "Jakob Poeltl", "Gary Trent Jr.", "Immanuel Quickley", "Lauri Markkanen", "John Collins", "Jordan Clarkson", "Collin Sexton", "Walker Kessler", "Talen Horton-Tucker", "Kyle Kuzma", "Jordan Poole", "Tyus Jones", "Deni Avdija", "Daniel Gafford", "Corey Kispert"));
        
        // generating the player IDs by utilizing the player names
        // the player IDs for this API are organized in this order:
        // first 5 letters of a player's last name, the first 2 letters of their first name, and a number at the end (usually 01 unless there are duplicates)
        // utilizing this formula to continue adding to the player IDs arraylist
        PLAYER_IDS = new ArrayList<>();
        for (String player : PLAYER_NAMES) {
            String playerID = "";
            int space = player.indexOf(" ");
            String lastName = player.substring(space + 1);
            String firstName = player.substring(0, space);
            
            if (lastName.length() <= 5) {
                playerID += lastName.toLowerCase();
            } else {
                playerID += lastName.substring(0, 5).toLowerCase();
            }

            playerID += firstName.substring(0, 2).toLowerCase();
            playerID += "01";
            PLAYER_IDS.add(playerID);
        }
    }

    // returns the player IDs to be utilized in the main app
    public static ArrayList<String> getAllPlayerIDs() {
        return new ArrayList<>(PLAYER_IDS);
    }

    // a random number generator to pick a random ID from the list of IDs
    public static int generateRandomNum() {
        return (int)(Math.random() * PLAYER_IDS.size());
    }

    // parsing the player into a player object
    public static Player parsePlayer(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);

        JsonObject player = root.getAsJsonObject("body");

        String name = player.get("firstName").getAsString() + " " + player.get("lastName").getAsString();
        String id = player.get("playerId").getAsString();

        String position = player.get("positions").getAsString();

        return new Player(name, id, position);
    }

    // parsing the player's season stats into a PlayerStats object
    public static PlayerStats parsePlayerSeasonStats(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject root = gson.fromJson(jsonResponse, JsonObject.class);

        JsonArray statsArray = root.getAsJsonArray("body");

        JsonObject statsData = statsArray.get(statsArray.size() - 2).getAsJsonObject();
        
        double ppg = statsData.get("pointsPerGame").getAsDouble();
        double rpg = statsData.get("defensiveReboundsPerGame").getAsDouble() + statsData.get("offensiveReboundsPerGame").getAsDouble();
        double apg = statsData.get("assistsPerGame").getAsDouble();
        double tov = statsData.get("turnoversPerGame").getAsDouble();
        double spg = statsData.get("stealsPerGame").getAsDouble();
        double bpg = statsData.get("blocksPerGame").getAsDouble();
        double fga = statsData.get("fieldGoalAttemptsPerGame").getAsDouble();
        double tpm = statsData.get("threePointFieldGoalsMadePerGame").getAsDouble();
        double fta = statsData.get("freeThrowAttemptsPerGame").getAsDouble();

        return new PlayerStats(ppg, rpg, apg, tov, spg, bpg, fga, tpm, fta);
    }

    public static void main(String[] args) {
        try {
            System.out.println(getNumAllStars("duranke01"));
            System.out.println(getNumChampionships("duranke01"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




