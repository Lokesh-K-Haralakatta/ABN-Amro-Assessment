package com.loki.recipes.pojos;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Recipe {
	private Integer id;
	private String name;
	private String type;
	private Integer servingCapacity;
	private List<Ingredient> ingredientsList = new ArrayList<>();
	private String instructions;
}
