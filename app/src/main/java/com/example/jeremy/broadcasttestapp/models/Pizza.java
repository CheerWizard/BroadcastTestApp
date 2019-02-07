package com.example.jeremy.broadcasttestapp.models;

import com.example.jeremy.broadcasttestapp.constants.OrderStates;

public class Pizza {

    private String name;
    private String description;
    private String ingredients;
    private double cost;
    private OrderStates orderStates;

    public Pizza(String name, String description, String ingredients ,  double cost, OrderStates orderStates) {
        this.ingredients = ingredients;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.orderStates = orderStates;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public OrderStates getOrderStates() {
        return orderStates;
    }

    public void setOrderStates(OrderStates orderStates) {
        this.orderStates = orderStates;
    }
}
