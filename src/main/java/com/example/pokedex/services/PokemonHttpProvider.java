package com.example.pokedex.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PokemonHttpProvider implements PropertyProviderInterface, LocalizedPropertyProviderInterface {

    private String pokemonData;
    private JSONObject rootObject;
    private String locale;

    public PokemonHttpProvider() {
        this.pokemonData = "";
        this.rootObject = null;
        this.locale = "en";
    }

    public void makeHttpRequest(String url, Integer pokemon_id)  {
        try {

            /* Setup HTTP Client */
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            /* Prepare HTTP request */
            HttpGet request = new HttpGet(url + pokemon_id);
            // necessary header to get a JSON response
            request.addHeader("content-type", "application/json");
            // run the request
            HttpEntity responseEntity = httpClient.execute(request).getEntity();

            // decode the response body into a Java string
            this.pokemonData = EntityUtils.toString(responseEntity, "UTF-8");
            
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void parseJSONData() {
        try {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(this.pokemonData);
            if (resultObject instanceof JSONObject) {
                this.rootObject = (JSONObject) resultObject;
            }
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    private String normalizeFlavorText(String text) {
        if (text == null) return null;

        return text
            .replace("\n", " ")
            .replace("\f", " ")
            .replaceAll("\\s+", " ")
            .trim();
    }

    @Override
    public Integer getIntProperty(String propertyName) {
        if (this.rootObject == null) return null;

        try {
            Integer propertyValue = Math.toIntExact((Long) this.rootObject.get(propertyName));
            return propertyValue;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public String extractLocalizedString(JSONArray array, String propertyName) {
        for (Object obj : array) {
            JSONObject JsonObj = (JSONObject) obj;
            JSONObject lang = (JSONObject) JsonObj.get("language");

            if (lang != null && this.locale.equals(lang.get("name"))) {

                if ("names".equals(propertyName)) {
                    return (String) JsonObj.get("name");
                }

                if ("flavor_text_entries".equals(propertyName)) {
                    return normalizeFlavorText((String) JsonObj.get("flavor_text"));
                }
            }
        }
        return null;
    }

    @Override
    public String getStringProperty(String propertyName) {
        if (this.rootObject == null) return null;

        try {
            Object value = this.rootObject.get(propertyName);

            /* Case 1: Simple string */
            if (value instanceof String) {
                return (String) value;
            }

            /* Case 2: Array */
            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                return extractLocalizedString(array, propertyName);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public void setStringPropertyLocale(String localeCode) {
        if (localeCode == null || localeCode.isBlank()) {
            this.locale = "en";
            return;
        }
        if (!localeCode.equals("en") && !localeCode.equals("fr")) {
            this.locale = "en";
            return;
        }
        this.locale = localeCode;
    }

    @Override
    public String getStringPropertyLocale() {
        return this.locale;
    }



    // public static void main(String[] args) {
    //     makeHttpRequest("https://pokeapi.co/api/v2/pokemon/", 1);
    //     //System.out.println(pokemonData);
    //     Integer height = new PokemonHttpProvider().getIntProperty("height");
    //     System.out.println("Height : " + height);
    //     Integer weight = new PokemonHttpProvider().getIntProperty("weight");
    //     System.out.println("Weight : " + weight);
    // }
}
