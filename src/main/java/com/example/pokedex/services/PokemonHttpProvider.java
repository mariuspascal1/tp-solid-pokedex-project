package com.example.pokedex.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PokemonHttpProvider implements PropertyProviderInterface {

    private static String pokemonData;

    public static void makeHttpRequest(String url, Integer pokemon_id)  {
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
            pokemonData = EntityUtils.toString(responseEntity, "UTF-8");
            //System.out.println("Response is : " + responseBody);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    @Override
    public Integer getIntProperty(String propertyName) {
        try {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(pokemonData);
            if (resultObject instanceof JSONObject) {
                JSONObject rootObject = (JSONObject) resultObject;
                Integer propertyValue = Math.toIntExact((Long) rootObject.get(propertyName));
                return propertyValue;
                //String name = (String) rootObject.get("name");
                //Integer height = Math.toIntExact((Long) rootObject.get("height"));
                //System.out.printf("Name: %s, Height: %d\n", name, height);
            }
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public String getStringProperty(String propertyName) {
        try {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(pokemonData);
            if (resultObject instanceof JSONObject) {
                JSONObject rootObject = (JSONObject) resultObject;
                String propertyValue = (String) rootObject.get(propertyName);
                return propertyValue;
                //String name = (String) rootObject.get("name");
                //Integer height = Math.toIntExact((Long) rootObject.get("height"));
                //System.out.printf("Name: %s, Height: %d\n", name, height);
            }
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }

    @Override
    public void setStringPropertyLocale(String localeCode) {
    }

    @Override
    public String getStringPropertyLocale() {
        return "";
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
