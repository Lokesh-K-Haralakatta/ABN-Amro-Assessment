package com.loki.recipes.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loki.recipes.dao.Recipe;
import com.loki.recipes.repository.RecipesRepository;

@Service
public class RecipesService {
	private static final Logger log = LogManager.getLogger(RecipesService.class.getName());
	
	@Autowired
	private RecipesRepository recipesRepo;
	
	public Recipe CreateRecipe(Recipe newRecipe) {
		return recipesRepo.save(newRecipe);
	}
	
	public Recipe GetRecipe(Integer id) {
		Optional<Recipe> optRecipe = recipesRepo.findById(id);
		if(optRecipe.isPresent())
			return optRecipe.get();
		else
			return null;
	}
	
	public List<Recipe> GetAllRecipes(){
		return recipesRepo.findAll();
	}
}
