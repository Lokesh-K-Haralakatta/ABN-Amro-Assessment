package com.loki.recipes.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loki.recipes.dao.RecipeEntity;
import com.loki.recipes.exceptions.BadRequestException;
import com.loki.recipes.exceptions.ErrorMessages;
import com.loki.recipes.exceptions.NoSuchRecipeFoundException;
import com.loki.recipes.exceptions.RecipeNotCreatedException;
import com.loki.recipes.pojos.Recipe;
import com.loki.recipes.service.RecipesService;
import com.loki.recipes.util.Util;

import lombok.extern.slf4j.Slf4j;

//Recipes Webservice ReST Controller Class with all required end points

@RestController
@RequestMapping("/api")
@Slf4j
public class RecipesController {
	@Autowired
	private RecipesService service;
	
	@PostMapping("/recipes/")
	public ResponseEntity<RecipeEntity> createRecipe(@RequestBody RecipeEntity recipe){
		log.info("Processing the request for /api/recipes/ to create new recipe");
		if(Util.checkRecipeValidity(recipe) == false) {
			log.error("Provided recipe instance is not valid, throwing Bad Request Exception");
			throw new BadRequestException(ErrorMessages.BAD_REQUEST_MSG);
		} else {
			log.info("Calling service.saveRecipeToRepository to save recipe into DB");
			RecipeEntity savedRecipe = service.saveRecipeToRepository(recipe);
			if(savedRecipe == null) {
				log.error("Service failed to save new recipe into DB");
				throw new RecipeNotCreatedException(ErrorMessages.INTERNAL_SERVER_ERR_MSG);
			}else {
				log.info("Service successfully saved new recipe into DB");
				return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
			}
		}
	}
	
	@GetMapping("/recipes/{id}")
	public ResponseEntity<RecipeEntity> getRecipe(@PathVariable Integer id) {
		log.info("Processing the request for /api/recipes/id to get existing recipe");
		RecipeEntity recipeEntity = service.GetRecipe(id);
		if(recipeEntity != null) {
			log.info("Requested recipe with id: "+id+" retrieved from DB");
			return ResponseEntity.status(HttpStatus.OK).body(recipeEntity);
		} else {
			log.error("Requested recipe with id: "+id+" not found in DB");
			throw new NoSuchRecipeFoundException(ErrorMessages.RECIPE_NOT_FOUND_MSG);
		}
	}
}
