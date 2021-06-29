package com.loki.recipes.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loki.recipes.dao.RecipeEntity;
import com.loki.recipes.pojos.Recipe;
import com.loki.recipes.repository.RecipesRepository;
import com.loki.recipes.util.Util;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipesService {
	
	@Autowired
	private RecipesRepository recipesRepo;
	
	//Method to save given new recipe onto persistence layer
	public RecipeEntity saveRecipeToRepository(RecipeEntity newRecipe) {
		//Map recipe to Recipe Entity
		//RecipeEntity recipe = mapRecipeToEntity(newRecipe);
		//Save current date time into recipe instance
		Optional<Date> currentDateTime = Util.getCurrentDateTime();
		if(currentDateTime.isPresent())
			log.info("Current DateTime to be set in recipe entity: "+currentDateTime.toString());
		else
			log.warn("Setting null to current date time field in recipe entity");
		newRecipe.setCreationDateTime(currentDateTime.get());
		return recipesRepo.save(newRecipe);
	}
	
	//Method to query and retrieve requested recipe based on it's id
	public RecipeEntity GetRecipe(Integer id) {
		Optional<RecipeEntity> optRecipe = recipesRepo.findById(id);
		if(optRecipe.isPresent())
			return optRecipe.get();
		else
			return null;
	}
	
	public List<RecipeEntity> GetAllRecipes(){
		return recipesRepo.findAll();
	}
}
