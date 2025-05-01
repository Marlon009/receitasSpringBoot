package com.edu.fatec.pg.receitas.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "receitas")

public class ReceitasModel {
    @Id
    @Column
    private int id;

    @Column(unique = true)
    private String name;
    @ElementCollection
    @CollectionTable(name = "receita_ingredientes", joinColumns = @JoinColumn(name = "receita_id"))
    @Column(name = "ingrediente")
    private List<String> ingredients;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private int servings;
    private String difficulty;


    public ReceitasModel(){

    }
    public ReceitasModel(int id, String name, List<String> ingredients, int prepTimeMinutes, int cookTimeMinutes, int servings, String difficulty) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.prepTimeMinutes = prepTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.servings = servings;
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "ReceitasModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", prepTimeMinutes=" + prepTimeMinutes +
                ", cookTimeMinutes=" + cookTimeMinutes +
                ", servings=" + servings +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}