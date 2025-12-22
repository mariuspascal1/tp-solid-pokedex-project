package com.example.pokedex.services;

public abstract class AbstractPokemonProviderService implements PropertyProviderInterface {
    /**
     * Charge les données d’un Pokémon (HTTP, DB, etc.)
     */
    public abstract void loadPokemon(Integer pokemonId);
}
