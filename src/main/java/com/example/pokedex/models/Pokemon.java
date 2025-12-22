package com.example.pokedex.models;

/**
 * Simple data container representing a Pokémon with no knowledge of where the
 * data comes from. The class sticks to the Single Responsibility Principle by
 * only modeling the domain state and related accessors, leaving retrieval and
 * presentation to dedicated services and views.
 */

public class Pokemon {
    private Integer id;
    private String name;
    private String description;
    private Integer height;
    private Integer weight;

    public Pokemon(Integer id, String name, String description, Integer height, Integer weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.height = height;
        this.weight = weight;
    }

    /**
     * Creates an empty Pokémon shell containing only its identifier.
     *
     * @param id the Pokédex identifier
     */
    public Pokemon(Integer id) {
        this(id, null, null, null, null);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Integer getWeight() {
        return this.weight;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) { 
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
