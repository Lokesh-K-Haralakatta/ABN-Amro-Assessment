package com.loki.recipes.repository;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loki.recipes.dao.Ingredient;
import com.loki.recipes.dao.Recipe;
import com.loki.recipes.util.TestUtil;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipesRepoTests {

	@Autowired
	private RecipesRepository repo;
	
	@Test
	public void SaveAndFindRecipeByIdTest() {
		//Get new sample recipe
		Recipe newRecipe = TestUtil.buildSampleRecipe(101,"Pulav","vg",10);
		//Save new recipe to repository
		repo.save(newRecipe);
		
		//Retrieve new recipe based on id
		Optional<Recipe> optionalRecipe = repo.findById(newRecipe.getId());
		
		//Validate there's recipe instance in optional
		assertTrue("Retrieved recipe is null",optionalRecipe.isPresent());
		
		//Get returned recipe from Optional
		Recipe retRecipe = optionalRecipe.get();
		
		//validate both recipes are equal
		assertEquals("Retrieved recipe is not equal to saved recipe",retRecipe, newRecipe);
		
		//Validate Ingredients present in retrieved recipe
		List<Ingredient> ingredientsList = TestUtil.convertJSONStringToIngredientsList(retRecipe.getIngredients());
		assertTrue("Number of ingredients are not matching with expected of 4",ingredientsList.size() == 4);
	}

	@Test
	public void SaveAndFindRecipesByCreationDateTimeTest() {
		//Get new sample recipe
		Recipe newRecipe = TestUtil.buildSampleRecipe(102,"Puliyogare","vg",6);
		//Save new recipe to repository
		repo.save(newRecipe);
		
		//Retrieve recipes based on creation date and time
		List<Recipe> recipes = repo.findRecipesByCreationDateTime(newRecipe.getCreationDateTime());
		
		//Validate there's exactly one recipe returned from repository
		assertTrue(recipes.size() == 1);
		
		//validate both recipes are having same creation date time value
		assertEquals("Saved recipe and retrieved recipe have different creation date time",
				 newRecipe.getCreationDateTime(),recipes.get(0).getCreationDateTime());
	}
	
	@Test
	public void SaveAndFindRecipesByTypeTest() {
		//Get and Save two recipes into repository with different type
		Recipe newRecipe1 = TestUtil.buildSampleRecipe(103,"Mutton Biriyani","ng",20);
		Recipe newRecipe2 = TestUtil.buildSampleRecipe(104,"Veg Biriyani","vg",10);
		repo.save(newRecipe1);
		repo.save(newRecipe2);
		
		//Retrieve all vegetarian recipes from repository
		List<Recipe> recipes = repo.findRecipesByType("vg");
		
		//Validate all retrieved recipes are of type vg
		recipes.forEach(recipe -> assertEquals("Recipe type is not equal to vg",recipe.getType(),"vg"));
		
		//Retrieve all vegetarian recipes from repository
		recipes = repo.findRecipesByType("ng");

		//Validate all retrieved recipes are of type ng
		recipes.forEach(recipe -> assertEquals("Recipe type is not equal to ng",recipe.getType(),"ng"));
	}
	
	@Test
	public void SaveAndFindRecipesByServingCapacityTest() {
		//Get new sample recipe
		Recipe newRecipe = TestUtil.buildSampleRecipe(105,"White Rice","vg",30);
		//Save new recipe to repository
		repo.save(newRecipe);
		
		//Retrieve recipes based on creation date and time
		List<Recipe> recipes = repo.findRecipesByServingCapacity(30);
		
		//Validate there's exactly one recipe returned from repository
		assertTrue(recipes.size() == 1);
		
		//validate both recipes are having same serving capacity value
		assertEquals("Saved recipe and retrieved recipe have different serving capacity",
				newRecipe.getServingCapacity(),recipes.get(0).getServingCapacity());
	}
}
