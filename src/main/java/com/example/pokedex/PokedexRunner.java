package com.example.pokedex;


import com.example.pokedex.utilities.AbstractPokedexRunner;

public class PokedexRunner extends AbstractPokedexRunner  {

    @Override
    public void onOptionsChange(DataSource dataSource, String dbPath) throws Exception {
        // write here your code that is run when the user changes the options
    }

    @Override
    public void runPokedex(Integer pokemonId) throws Exception {
        // TODO: modify with real data
        System.out.printf("=============================\n");
        System.out.printf("Pokémon # %s\n", pokemonId);
        System.out.printf("Nom : Bulbizarre\n");
        System.out.printf("Description : Il a une graine qui pousse sur son dos\n");
        System.out.printf("Taille : 7\n");
        System.out.printf("Poids : 69\n");
        System.out.printf("=============================\n");
    }
}
