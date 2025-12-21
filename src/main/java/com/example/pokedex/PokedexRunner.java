package com.example.pokedex;

import com.example.pokedex.models.Pokemon;
import com.example.pokedex.controllers.PokemonController;
import com.example.pokedex.views.PokemonView;
import com.example.pokedex.utilities.AbstractPokedexRunner;

public class PokedexRunner extends AbstractPokedexRunner  {


    @Override
    public void onOptionsChange(DataSource dataSource, String dbPath) throws Exception {
        // write here your code that is run when the user changes the options
    }

    @Override
    public void runPokedex(Integer pokemonId) throws Exception {
        Pokemon pokemon = new Pokemon(pokemonId);
        PokemonController pokemonController = new PokemonController();
        pokemonController.setProperties(pokemon, pokemonId);
        PokemonView pokemonView = new PokemonView();
        pokemonView.print(pokemon);
    }
}
