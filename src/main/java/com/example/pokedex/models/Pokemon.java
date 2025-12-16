package com.example.pokedex.models;

public class Pokemon {
    private int id;
    private String name;
    private String description;
    private int height;
    private int weight;

    public Pokemon(int id, String name, String description, int height, int weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.height = height;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }
}
