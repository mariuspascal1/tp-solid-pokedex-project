package com.example.pokedex.views;

import com.example.pokedex.models.Pokemon;

/**
 * Responsible for rendering a textual representation of a {@link Pokemon}.
 */
public class PokemonView {
    

    /**
     * Builds the formatted string representing the provided {@link Pokemon}.
     *
     * @param pokemon the pokemon to render
     * @return the formatted representation ready to be displayed
     */
    public String render(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null");
        }

        StringBuilder builder = new StringBuilder();
        builder.append("=============================\n");
        builder.append(String.format("Pokémon # %d%n", pokemon.getId()));
        builder.append(String.format("Nom : %s%n", pokemon.getName()));
        builder.append(String.format("Description : %s%n", pokemon.getDescription()));
        builder.append(String.format("Taille : %d%n", pokemon.getHeight()));
        builder.append(String.format("Poids : %d%n", pokemon.getWeight()));
        builder.append("=============================\n");
        return builder.toString();
    }

    /**
     * Prints the rendered representation to the standard output.
     *
     * @param pokemon the pokemon to display
     */
    public void print(Pokemon pokemon) {
        System.out.print(render(pokemon));
    }
}