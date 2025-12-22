package com.example.pokedex.services;

/**
 * Abstraction shared by all Pokémon data providers. Exposing common behavior
 * through this base class supports the Open/Closed Principle by allowing new
 * sources to extend it, and it enables Dependency Inversion when controllers
 * depend on this abstraction instead of concrete implementations.
 */

public abstract class AbstractPokemonProviderService implements PropertyProviderInterface {
    /**
     * Loads Pokémon data from the underlying source (HTTP or local database.).
     *
     * @param pokemonId the identifier of the Pokémon to retrieve
     */
    public abstract void loadPokemon(Integer pokemonId);
}
