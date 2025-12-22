package com.example.pokedex;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.PokemonHttpProvider;
import com.example.pokedex.controllers.PokemonController;
import com.example.pokedex.views.PokemonView;
import com.example.pokedex.utilities.AbstractPokedexRunner;

public class PokedexRunner extends AbstractPokedexRunner  {

    private PokemonHttpProvider pokemonService = new PokemonHttpProvider();
    private PokemonController pokemonController = new PokemonController(pokemonService);

    @Override
    public void onOptionsChange(DataSource dataSource, String dbPath) throws Exception {
        setupServiceLocale(pokemonService);
    }

    @Override
    public void runPokedex(Integer pokemonId) throws Exception {
        Pokemon pokemon = new Pokemon(pokemonId);
        pokemonController.setProperties(pokemon, pokemonId);
        new PokemonView().print(pokemon);
    }
}
