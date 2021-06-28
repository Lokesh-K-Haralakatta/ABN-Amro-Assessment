package com.loki.recipes.dao;

import lombok.Data;

@Data
public class Ingredient {
	private String name;
	private String quantity;
	
	public Ingredient() {}
	
	public Ingredient(String n, String qty) {
		this.name = n;
		this.quantity = qty;
	}
}
