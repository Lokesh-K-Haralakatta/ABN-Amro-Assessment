package com.loki.recipes.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.loki.recipes.dao.RecipeEntity;
import com.loki.recipes.pojos.Recipe;

import lombok.extern.slf4j.Slf4j;

//Util Class to contain common utility methods
@Slf4j
public class Util {

	//Method to Get and return current date along with time in required format
	public static Optional<Date> getCurrentDateTime() {
		String pattern = "dd-MM-yyyy HH:mm";
		try {
			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date currentDateTime = Calendar.getInstance().getTime();
			log.info("Current Date Time Value: "+currentDateTime.toString());
			return Optional.of(currentDateTime);
		}catch(Exception e) {
			log.error("Exception caught while parsing current datetime into format: "+pattern);
			ExceptionUtils.getStackTrace(e);
			return Optional.empty();
		}
	}

	//Method to map recipe instance to RecipeEntity
	public static RecipeEntity mapRecipeToEntity(Recipe newRecipe) {
		// TODO Auto-generated method stub
		return null;
	}

	//Method to validate various fields present in given Recipe Entity
	public static Boolean checkRecipeValidity(RecipeEntity recipe) {
		//Check for nullness
		if(recipe == null) {
			log.error("Given recipe instance is null");
			return false;
		} else if (recipe.getId() == null || recipe.getName() == null || 
				   recipe.getType() == null || recipe.getServingCapacity() == null) {
			log.error("One of non-null field is null in recipe instance");
			return false;
		} else
			return true;
	}
}
