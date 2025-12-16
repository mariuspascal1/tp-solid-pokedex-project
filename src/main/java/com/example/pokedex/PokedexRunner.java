package com.example.pokedex;


import com.example.pokedex.utilities.AbstractPokedexRunner;

public class PokedexRunner extends AbstractPokedexRunner  {

    @Override
    public void onOptionsChange(DataSource dataSource, String dbPath) throws Exception {
        // write here your code that is run when the user changes the options
    }

    @Override
    public void runPokedex(Integer pokemonId) throws Exception {
        /* Write your code here */
        System.out.printf("Pokemon %s was requested", pokemonId); // TODO remove
    }
}
