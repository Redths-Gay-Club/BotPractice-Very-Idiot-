package com.njdge.botpractice;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {
    public static String getUUIDFromName(String playerName) {
        String uuid = null;

        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            if (response.length() > 0) {
                uuid = response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return uuid;
    }
    private static final String MOJANG_API_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";

    public static String getSkinTexture(UUID uuid) {
        try {
            String url = MOJANG_API_URL + uuid.toString().replace("-", "");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray properties = (JSONArray) jsonObject.get("properties");
            for (Object property : properties) {
                JSONObject propertyObj = (JSONObject) property;
                String name = (String) propertyObj.get("name");
                if ("textures".equals(name)) {
                    String value = (String) propertyObj.get("value");
                    return value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getSkinSignature(UUID uuid) {
        try {
            String url = MOJANG_API_URL + uuid.toString().replace("-", "");
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray properties = (JSONArray) jsonObject.get("properties");
            for (Object property : properties) {
                JSONObject propertyObj = (JSONObject) property;
                String name = (String) propertyObj.get("name");
                if ("textures".equals(name)) {
                    String signature = (String) propertyObj.get("signature");
                    return signature;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

