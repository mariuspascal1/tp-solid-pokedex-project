package com.example.pokedex.controllers;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.AbstractPokemonProviderService;

public class PokemonController {

    private final AbstractPokemonProviderService provider;

    public PokemonController(AbstractPokemonProviderService provider) {
        this.provider = provider;
    }

    public void setProperties(Pokemon pokemon, Integer pokemonId) {

        provider.loadPokemon(pokemonId);

        pokemon.setHeight(provider.getIntProperty("height"));
        pokemon.setWeight(provider.getIntProperty("weight"));
        pokemon.setName(provider.getStringProperty("names"));
        pokemon.setDescription(provider.getStringProperty("flavor_text_entries"));
    }
    
}