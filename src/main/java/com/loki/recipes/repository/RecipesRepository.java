package com.loki.recipes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loki.recipes.dao.Recipe;

@Repository
public interface RecipesRepository extends JpaRepository<Recipe,Integer> {
	//Method to retrieve all recipes from repository matching with given DateTime value
	List<Recipe> findRecipesByCreationDateTime(Date dateTime);
	
	//Method to retrieve all recipes from repository matching with given recipe type
	List<Recipe> findRecipesByType(String type);
	
	//Method to retrieve all recipes from repository matching with given serving capacity
	List<Recipe> findRecipesByServingCapacity(Integer capacity);
	
}
