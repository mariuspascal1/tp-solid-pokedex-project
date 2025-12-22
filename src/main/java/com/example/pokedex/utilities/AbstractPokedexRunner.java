package com.example.pokedex.utilities;

import com.example.pokedex.services.LocalizedPropertyProviderInterface;
import com.example.pokedex.services.PokemonHttpProvider;
import com.example.pokedex.services.PokemonSqliteProvider;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.Scanner;
import java.util.Set;

/**
 * Abstraction of a Pokedex runner that handles user interaction through the
 * console. This class manages the configuration of data sources and locales,
 * and delegates Pokémon retrieval and display to concrete implementations. 
 * It adheres to the Liskov Substitution Principle by allowing any subclass 
 * to be used interchangeably without altering the expected behavior. Concrete 
 * implementations such as {@link PokemonHttpProvider} and 
 * {@link PokemonSqliteProvider} can be substituted transparently without 
 * altering the behavior of the application, as they all respect the contract 
 * defined by the abstract base class.
 */

public abstract class AbstractPokedexRunner {

    public enum DataSource {
        POKEAPI, LOCAL_DATABASE
    }

    public Set<String> availableLocales = Set.of("en", "fr");

    private DataSource dataSource;
    private String locale;
    private String databasePath = "";
    public static Scanner scanner = new Scanner(System.in);


    public void start() throws Exception {
        this.prompt();
    }

    private void prompt() throws Exception {
        this.promptOptions();

        // Prompt for chat size warning preference
        while (true) {
            System.out.println(this.displayCurrentOptions());
            System.out.println("Enter the ID of the pokemon to display (enter 'o' to change the options, 'q' to exit)");

            String userInput = scanner.nextLine().trim();

            if (userInput.equals("o")) {
                this.promptOptions();
            } else if (userInput.equals("q")) {
                break;
            } else {
                try {
                    Integer pokemonId = Integer.parseInt(userInput);
                    this.runPokedex(pokemonId);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }

    private void promptOptions() throws Exception {
        System.out.println("\n\n==== Setup Pokedex Options ====");


        // Prompt for preferred dataSource
        while (true) {
            System.out.println("Choose data source : ");
            System.out.println("[1] Pokeapi (HTTP)");
            System.out.println("[2] Local SQLite database");
            String userInput = scanner.nextLine().trim();

            if (userInput.equals("1")) {
                this.dataSource = DataSource.POKEAPI;
                break;
            } else if (userInput.equals("2")) {
                this.dataSource = DataSource.LOCAL_DATABASE;
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        // if database selected
        // Prompt for database path
        if (this.dataSource == DataSource.LOCAL_DATABASE) {
            while (true) {
                if (this.databasePath.isEmpty()) {
                    System.out.println("Enter database path:");
                } else {
                    System.out.printf("Enter database path (current path: '%s'): \n", this.databasePath);
                }

                String dbPath = scanner.nextLine().trim();

                if (dbPath.isEmpty() && !this.databasePath.isEmpty()) {
                    dbPath = this.databasePath;
                }

                String jdbcUrl = "jdbc:sqlite:" + dbPath;
                SQLiteConfig config = new SQLiteConfig();
                config.setEncoding(SQLiteConfig.Encoding.UTF8);
                try {
                    DriverManager.getConnection(jdbcUrl, config.toProperties())
                        .prepareStatement("SELECT id, name, description, height, weight FROM pokemons WHERE id = 1")
                        .executeQuery();
                } catch (SQLException e) {
                    System.out.println("Invalid database path");
                    continue;
                }
                this.databasePath = dbPath;
                break;
            }
        }

        // Prompt for locale
        while (true) {
            System.out.println("Choose locale : 'en' (default), or 'fr':");
            String userInput = scanner.nextLine().trim();

            if (this.availableLocales.contains(userInput)) {
                this.locale = userInput;
                break;
            } else if (userInput.isEmpty()) {
                System.out.println("Using 'en' as default");
                this.locale = "en";
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
        System.out.println("==== End of options setup ====\n");
        this.onOptionsChange(this.dataSource, this.databasePath);

    }

    private String displayCurrentOptions() {
        String dataSourceStr;
        if (this.dataSource == DataSource.POKEAPI) {
            dataSourceStr = "PokéAPI";
        } else {
            dataSourceStr = String.format("Local database (%s)", this.databasePath);
        }

        return String.format("(Current options, data source: %s, locale: %s)", dataSourceStr, this.locale);
    }

    public abstract void runPokedex(Integer pokemonId) throws Exception;
    public void onOptionsChange(DataSource dataSource, String dbPath) throws Exception {}

    /** Modified due to the modification for the Interface Segregation principle
     * 
     * @param service the service to configure with the chosen locale
     */

    public void setupServiceLocale(LocalizedPropertyProviderInterface service) { 
        service.setStringPropertyLocale(this.locale);
    }

}
