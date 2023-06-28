package features;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SkyBlockSecretsCounter {
    private static String API_KEY = "";

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("/api")) {
            if (args.length > 1 && args[1].equalsIgnoreCase("new")) {
                if (args.length > 2) {
                    API_KEY = args[2];
                    System.out.println("API key updated successfully!");
                } else {
                    System.out.println("Please provide a valid API key.");
                    return;
                }
            } else {
                System.out.println("Invalid command. Please use '/api new <API_KEY>' to set a new API key.");
                return;
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Minecraft username: ");
        String username = scanner.nextLine();
        scanner.close();

        String playerUUID = getPlayerUUID(username);
        if (playerUUID == null) {
            System.out.println("Failed to retrieve the player's UUID. Make sure the username is correct.");
            return;
        }

        try {
            URL url = new URL("https://api.hypixel.net/player?key=" + API_KEY + "&uuid=" + playerUUID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String jsonString = response.toString();

            int secretsFound = parseSecretsCount(jsonString);

            System.out.println("Secrets found: " + secretsFound);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getPlayerUUID(String username) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String jsonString = response.toString();

            String playerUUID = parsePlayerUUID(jsonString);
            return playerUUID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String parsePlayerUUID(String jsonString) {
        String playerUUID = null;
        try {
            jsonString = jsonString.replaceAll("\\s", "");
            int startIndex = jsonString.indexOf("\"id\":\"");
            int endIndex = jsonString.indexOf("\",");
            playerUUID = jsonString.substring(startIndex + 6, endIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerUUID;
    }

    private static int parseSecretsCount(String jsonString) {
        int secretsFound = -1;
        try {
            jsonString = jsonString.replaceAll("\\s", "");
            int startIndex = jsonString.indexOf("\"secrets_found\":");
            int endIndex = jsonString.indexOf(",", startIndex);
            String secretsFoundStr = jsonString.substring(startIndex + 16, endIndex);
            secretsFound = Integer.parseInt(secretsFoundStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretsFound;
    }
}
