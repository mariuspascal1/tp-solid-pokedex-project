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

/**
 * Retrieves Pokémon data from the public PokeAPI and exposes selected
 * properties, including localized strings for supported locales.
 */
public class PokemonHttpProvider extends AbstractPokemonProviderService implements LocalizedPropertyProviderInterface {

    private String pokemonData;
    private JSONObject rootObject;
    private JSONObject rootObjectSpecies;
    private String locale;

    /**
     * Creates a provider configured to use English localization by default.
     */
    public PokemonHttpProvider() {
        this.pokemonData = "";
        this.rootObject = null;
        this.rootObjectSpecies = null;
        this.locale = "en";
    }

    /**
     * Fetches detailed Pokémon information and associated species metadata
     * from the PokeAPI endpoints, caching results for property accessors.
     *
     * @param pokemonId the identifier of the Pokémon to load
     */
    @Override
    public void loadPokemon(Integer pokemonId) {
        makeHttpRequest("https://pokeapi.co/api/v2/pokemon/", pokemonId);
        this.rootObject = parseJSONData(this.pokemonData);

        makeHttpRequest("https://pokeapi.co/api/v2/pokemon-species/", pokemonId);
        this.rootObjectSpecies = parseJSONData(this.pokemonData);
    }

    /**
     * Performs an HTTP GET request to the given endpoint, storing the response
     * body for later parsing.
     *
     * @param url        base URL to query
     * @param pokemon_id identifier of the Pokémon to fetch
     */
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

    /**
     * Parses the provided JSON payload into a {@link JSONObject} instance.
     *
     * @param jsonData raw JSON text returned by the API
     * @return a parsed {@link JSONObject} or {@code null} if parsing fails
     */
    public JSONObject parseJSONData(String jsonData) {
        try {
            JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(jsonData);
            if (resultObject instanceof JSONObject) {
                return (JSONObject) resultObject;
            }
        } catch (ParseException e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * Removes formatting artifacts commonly present in flavor text entries.
     *
     * @param text the raw flavor text to normalize
     * @return a cleaned version of the text with whitespace normalized
     */
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

    /**
     * Retrieves a localized string from an array of objects that include a
     * language descriptor.
     *
     * @param array         the JSON array containing language-keyed entries
     * @param propertyName  the property source ("names" or "flavor_text_entries")
     * @return the value matching the current locale or {@code null} if absent
     */
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
        if (this.rootObjectSpecies == null) return null;

        String realPropertyName = propertyName;
        if ("description".equals(propertyName)) {
            realPropertyName = "flavor_text_entries";
        }
        if ("name".equals(propertyName)) {
            realPropertyName = "names";
        }

        try {
            Object value = this.rootObjectSpecies.get(realPropertyName);

            /* Case 1: Simple string */
            if (value instanceof String) {
                return (String) value;
            }

            /* Case 2: Array */
            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                return extractLocalizedString(array, realPropertyName);
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

}
