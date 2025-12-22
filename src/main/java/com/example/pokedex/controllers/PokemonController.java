package com.example.pokedex.controllers;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.PokemonHttpProvider;

public class PokemonController {

    private final PokemonHttpProvider pokemonHttpProvider;

    public PokemonController(PokemonHttpProvider pokemonHttpProvider) {
        this.pokemonHttpProvider = pokemonHttpProvider;
    }

    public void setProperties(Pokemon pokemon, Integer pokemonId) {

        this.pokemonHttpProvider.makeHttpRequest("https://pokeapi.co/api/v2/pokemon/", pokemonId);
        this.pokemonHttpProvider.parseJSONData();

        Integer height = this.pokemonHttpProvider.getIntProperty("height");
        Integer weight = this.pokemonHttpProvider.getIntProperty("weight");
        pokemon.setHeight(height);
        pokemon.setWeight(weight);

        this.pokemonHttpProvider.makeHttpRequest("https://pokeapi.co/api/v2/pokemon-species/", pokemonId);
        this.pokemonHttpProvider.parseJSONData();

        String name = this.pokemonHttpProvider.getStringProperty("names");
        String flavor_text_entry = this.pokemonHttpProvider.getStringProperty("flavor_text_entries");
        pokemon.setName(name);
        pokemon.setDescription(flavor_text_entry);
    }
    
}