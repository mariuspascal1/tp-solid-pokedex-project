package com.example.pokedex.services;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class PokemonSqliteProvider implements PropertyProviderInterface {

    String name;
    String description;
    Integer height;
    Integer weight;
    String databaseFilePath;

    public PokemonSqliteProvider() {
        this.databaseFilePath = "./ressources/pokemondatabase.sqlite";

        this.name = null;
        this.description = null;
        this.height = null;
        this.weight = null;
    }

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
        // Implementation for retrieving integer properties from SQLite database
        if ("height".equals(propertyName)) {
            return this.height;
        } else if ("weight".equals(propertyName)) {
            return this.weight;
        }
        return null;
    }

    @Override
    public String getStringProperty(String propertyName) {
        // Implementation for retrieving string properties from SQLite database
        if ("name".equals(propertyName)) {
            return this.name;
        } else if ("description".equals(propertyName)) {
            return this.description;
        }
        return null;
    }
    
}
