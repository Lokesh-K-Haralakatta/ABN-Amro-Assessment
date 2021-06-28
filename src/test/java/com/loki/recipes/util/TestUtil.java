package com.loki.recipes.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loki.recipes.dao.Ingredient;
import com.loki.recipes.dao.Recipe;

//Class to contain common test utility methods
public class TestUtil {
	private static final Logger log = LogManager.getLogger(TestUtil.class.getName());
	
	//Get and return current date along with time in required format
	public static Date getCurrentDateTime() {
		String pattern = "dd-MM-yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.getCalendar().getTime();
	}
	
	//Build and return list of ingredients
	public static List<Ingredient> buildIngredients(){
		List<Ingredient> ingredientsList = new ArrayList<>();
		
		Ingredient ing = new Ingredient("ing-1","1 tbsp");ingredientsList.add(ing);
		ing = new Ingredient("ing-2","2 nos");ingredientsList.add(ing);
		ing = new Ingredient("ing-3","3 kgs");ingredientsList.add(ing);
		ing = new Ingredient("ing-4","4 ml");ingredientsList.add(ing);
		
		return ingredientsList;
	}
	
	//Convert given ingredients list to JsonString and return
	public static String convertToJSONString(List<Ingredient> ingList) {
		ObjectMapper mapper = new ObjectMapper(); 
		String jsonString = null;
		try {
				jsonString = mapper.writeValueAsString(ingList); 
		} catch(Exception e) {
			log.error("Exception caught while converting List to JSON String");
			e.printStackTrace();
		}
		return jsonString;
	}
	
	//Convert given JSON String to List of Ingredients
	public static List<Ingredient> convertJSONStringToIngredientsList(String jsonString){
		ObjectMapper mapper = new ObjectMapper();
		List<Ingredient> ingredientsList = null;
		//Convert JSON array to Array objects
	    //Ingredient[] ingredients = mapper.readValue(jsonString, Ingredient[].class);
	    try {
	    	//Convert JSON array to List of objects
	    	ingredientsList = Arrays.asList(mapper.readValue(jsonString, Ingredient[].class));
	    } catch(Exception e) {
	    	log.error("Exception caught while converting JSON String to Ingredients List");
	    	e.printStackTrace();
	    }
	    return ingredientsList;
	}
	
	//Build and return instructions as String
	public static String buildInstructions() {
		String instructions = "1. Step-1: \n2. Step-2: \n3. Step-3: \n 4. Step-4: \n";
		
		return instructions;
	}
	
	//Build sample recipe instance using utility methods and return
	public static Recipe buildSampleRecipe(Integer id,String name,String type,Integer capacity) {
		Recipe newRecipe = new Recipe();
		newRecipe.setId(id);
		newRecipe.setName(name);
		newRecipe.setType(type);
		newRecipe.setServingCapacity(capacity);
		newRecipe.setIngredients(TestUtil.convertToJSONString(TestUtil.buildIngredients()));
		newRecipe.setCreationDateTime(TestUtil.getCurrentDateTime());
		newRecipe.setInstructions(TestUtil.buildInstructions());
		return newRecipe;
	}
}
