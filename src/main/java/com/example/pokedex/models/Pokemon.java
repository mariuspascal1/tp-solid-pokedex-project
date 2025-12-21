package com.example.pokedex.models;

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

    public Pokemon() {
        new Pokemon(null, null, null, null, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { 
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
