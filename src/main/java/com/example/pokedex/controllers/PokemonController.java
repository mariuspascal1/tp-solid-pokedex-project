package com.example.pokedex.controllers;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.PokemonHttpProvider;

public class PokemonController {

    private final PokemonHttpProvider pokemonHttpProvider;

    public PokemonController(PokemonHttpProvider pokemonHttpProvider) {
        this.pokemonHttpProvider = pokemonHttpProvider;
    }

    public void setProperties(Pokemon pokemon, Integer pokemonId) {

        pokemonHttpProvider.makeHttpRequest("https://pokeapi.co/api/v2/pokemon/", pokemonId);

        Integer height = pokemonHttpProvider.getIntProperty("height");
        Integer weight = pokemonHttpProvider.getIntProperty("weight");
        pokemon.setHeight(height);
        pokemon.setWeight(weight);

        pokemonHttpProvider.makeHttpRequest("https://pokeapi.co/api/v2/pokemon-species/", pokemonId);

        String name = pokemonHttpProvider.getStringProperty("names");
        String flavor_text_entry = pokemonHttpProvider.getStringProperty("flavor_text_entries");
        pokemon.setName(name);
        pokemon.setDescription(flavor_text_entry);
    }
    
}