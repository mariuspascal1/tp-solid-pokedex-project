package com.example.pokedex.controllers;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.AbstractPokemonProviderService;

public class PokemonController {

    private final AbstractPokemonProviderService provider;

    /**
     * Creates a controller that delegates data loading to the given provider.
     *
     * @param provider the service used to retrieve Pokémon properties
     */
    public PokemonController(AbstractPokemonProviderService provider) {
        this.provider = provider;
    }
    
    /**
     * Loads properties for a Pokémon using the provider and assigns them to the
     * provided {@link Pokemon} instance.
     *
     * @param pokemon   the Pokémon model to enrich with data
     * @param pokemonId the identifier of the Pokémon to retrieve
     */
    public void setProperties(Pokemon pokemon, Integer pokemonId) {

        provider.loadPokemon(pokemonId);

        pokemon.setHeight(provider.getIntProperty("height"));
        pokemon.setWeight(provider.getIntProperty("weight"));
        pokemon.setName(provider.getStringProperty("name"));
        pokemon.setDescription(provider.getStringProperty("description"));
    }
    
}