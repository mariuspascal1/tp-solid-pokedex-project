package com.example.pokedex;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.services.AbstractPokemonProviderService;
import com.example.pokedex.services.PokemonHttpProvider;
import com.example.pokedex.services.PokemonSqliteProvider;
import com.example.pokedex.controllers.PokemonController;
import com.example.pokedex.views.PokemonView;
import com.example.pokedex.utilities.AbstractPokedexRunner;

public class PokedexRunner extends AbstractPokedexRunner  {

    private AbstractPokemonProviderService pokemonService;
    private PokemonController pokemonController;

    @Override
    public void onOptionsChange(DataSource dataSource, String dbPath) throws Exception {

        switch (dataSource) {

            case POKEAPI:
                pokemonService = new PokemonHttpProvider();
                setupServiceLocale((PokemonHttpProvider) pokemonService);
                break;

            case LOCAL_DATABASE:
                pokemonService = new PokemonSqliteProvider(dbPath);
                break;

            default:
                throw new IllegalStateException("Unknown data source");
        }

        pokemonController = new PokemonController(pokemonService);
    }

    @Override
    public void runPokedex(Integer pokemonId) throws Exception {
        Pokemon pokemon = new Pokemon(pokemonId);
        pokemonController.setProperties(pokemon, pokemonId);
        new PokemonView().print(pokemon);
    }
}
