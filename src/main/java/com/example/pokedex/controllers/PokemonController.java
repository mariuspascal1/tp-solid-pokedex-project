package com.example.pokedex.controllers;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.PokemonHttpProvider;

public class PokemonController {

    private final PokemonHttpProvider pokemonHttpProvider;

    public PokemonController() {
        this.pokemonHttpProvider = new PokemonHttpProvider();
    }

    public void setProperties(Pokemon pokemon, Integer pokemonId) {

        this.pokemonHttpProvider.makeHttpRequest("https://pokeapi.co/api/v2/pokemon/", pokemonId);

        Integer height = this.pokemonHttpProvider.getIntProperty("height");
        Integer weight = this.pokemonHttpProvider.getIntProperty("weight");
        pokemon.setHeight(height);
        pokemon.setWeight(weight);

        this.pokemonHttpProvider.makeHttpRequest("https://pokeapi.co/api/v2/pokemon-species/", pokemonId);

        String name = this.pokemonHttpProvider.getStringProperty("names");
        String flavor_text_entry = this.pokemonHttpProvider.getStringProperty("flavor_text_entries");
        pokemon.setName(name);
        pokemon.setDescription(flavor_text_entry);
    }
    
}