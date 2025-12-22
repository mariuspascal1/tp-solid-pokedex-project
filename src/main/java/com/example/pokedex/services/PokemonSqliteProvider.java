package com.example.pokedex.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

/**
 * Provides Pokémon properties by querying a SQLite database.
 */
public class PokemonSqliteProvider extends AbstractPokemonProviderService {

    private final String databaseFilePath;

    private String name;
    private String description;
    private Integer height;
    private Integer weight;

    /**
     * Builds a provider that connects to the specified SQLite database file.
     *
     * @param databaseFilePath absolute or relative path to the SQLite database
     */
    public PokemonSqliteProvider(String databaseFilePath) {
        this.databaseFilePath = databaseFilePath;

        this.name = null;
        this.description = null;
        this.height = null;
        this.weight = null;
    }

    /**
     * Loads Pokémon attributes from the SQLite database by delegating to a
     * dedicated query method.
     *
     * @param pokemonId the identifier of the Pokémon to fetch
     */
    @Override
    public void loadPokemon(Integer pokemonId) {
        makeDbRequest(pokemonId);
    }

    /**
     * Executes a database query to fetch the Pokémon row and caches its
     * properties locally.
     *
     * @param pokemon_id the identifier of the Pokémon to search in the database
     */
    public void makeDbRequest(Integer pokemon_id) {
        try {

            /* Setup database path */
            String databaseFilePath = this.databaseFilePath;
            String url = "jdbc:sqlite:" + databaseFilePath;

            /* Open database connection */
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            Connection dbConnection = DriverManager.getConnection(url, config.toProperties());

            /* Prepare query */
            PreparedStatement stmt  = dbConnection.prepareStatement("SELECT id, name, description, height, weight FROM pokemons WHERE id = ?");
            stmt.setInt(1, pokemon_id);

            /* Run query */
            ResultSet pokemonResultSet = stmt.executeQuery();

            /* Navigate the Result Set (iterator pattern) */
            while (pokemonResultSet.next()) {
                this.name = pokemonResultSet.getString("name");
                this.description = pokemonResultSet.getString("description");
                this.height = pokemonResultSet.getInt("height");
                this.weight = pokemonResultSet.getInt("weight");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public Integer getIntProperty(String propertyName) {
        /** 
         * Implementation for retrieving integer properties from SQLite database */
        if ("height".equals(propertyName)) {
            return this.height;
        } else if ("weight".equals(propertyName)) {
            return this.weight;
        }
        return null;
    }

    @Override
    public String getStringProperty(String propertyName) {
        /** 
         * Implementation for retrieving string properties from SQLite database */
        if ("name".equals(propertyName)) {
            return this.name;
        } else if ("description".equals(propertyName)) {
            return this.description;
        }
        return null;
    }
    
}
