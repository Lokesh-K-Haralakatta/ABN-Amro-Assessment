package com.loki.recipes.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.loki.recipes.dao.RecipeEntity;
import com.loki.recipes.exceptions.ErrorMessages;
import com.loki.recipes.exceptions.ErrorResponse;
import com.loki.recipes.test.util.TestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RecipesControllerTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	void GivenInvalidRecipeId_WhenRequestedToRetrieve_ThenResponseIsNotFound_Test() {
		//Prepare URL Path and parameters value
		Integer invalidRecipeId = 33;
		String baseURL = "http://localhost:"+port+"/api/recipes/";
		String apiPath = baseURL+invalidRecipeId;
		
		//Make client request to /api/recipes/{id} path with an invalid recipe id
		ResponseEntity<ErrorResponse> invalidIdResponse = restTemplate.getForEntity(apiPath, ErrorResponse.class);
		
		//Validate Http Status in response entity is NOT_FOUND
		assertThat(invalidIdResponse.getStatusCode()).as("Http Status is not as expected").isEqualTo(HttpStatus.NOT_FOUND);
		
		//Validate error message present in response entity body
		assertThat(invalidIdResponse.getBody().getMessage()).as("Error message returned in response entity is not as expected")
						.contains(ErrorMessages.RECIPE_NOT_FOUND_MSG);
	}

	@Test
	void GivenRecipeWithNulls_WhenPosted_ThenResponseIsBadRequest_Test() {
		//Prepare POST request details with recipe instance as null
		String baseURL = "http://localhost:"+port+"/api/recipes/";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<RecipeEntity> request = new HttpEntity<>(new RecipeEntity(), headers);
	    
	    //Make POST Request with recipe instance as null
	    ResponseEntity<ErrorResponse> errorResponse = restTemplate.postForEntity(baseURL, request, ErrorResponse.class);
	    
	    //Validate Http Status in response entity is BAD_REQUEST
	    assertThat(errorResponse.getStatusCode()).as("Http Status is not as expected").isEqualTo(HttpStatus.BAD_REQUEST);
		
	    //Validate error message present in response entity body
	  	assertThat(errorResponse.getBody().getMessage()).as("Error message returned in response entity is not as expected")
	  						     .contains(ErrorMessages.BAD_REQUEST_MSG);
	}
	
	@Test
	void GivenValidRecipe_WhenPosted_ThenResponseIsCreated_Test() {
		//Prepare POST request details with recipe instance as null
		String baseURL = "http://localhost:"+port+"/api/recipes/";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    RecipeEntity newRecipe = TestUtil.buildSampleRecipe(101, "Butter-Sponge-Cake", "eg", 5);
	    HttpEntity<RecipeEntity> request = new HttpEntity<>(newRecipe, headers);
	    
	    //Make POST Request with valid recipe instance
	    ResponseEntity<RecipeEntity> postResponse = restTemplate.postForEntity(baseURL, request, RecipeEntity.class);
	    
	    //Validate Http Status in response entity is CREATED
	    assertThat(postResponse.getStatusCode()).as("Http Status is not as expected").isEqualTo(HttpStatus.CREATED);
		
	    //Validate response entity body has recipe fields same as earlier recipe fields
	    RecipeEntity savedRecipe = postResponse.getBody(); 
	    assertThat(savedRecipe.getId()).as("Saved Recipe Id not matched with given recipe Id")
		  						.isEqualTo(newRecipe.getId());
	    assertThat(savedRecipe.getName()).as("Saved Recipe Name not matched with given recipe Name")
		  						.isEqualTo(savedRecipe.getName());
	    assertThat(savedRecipe.getCreationDateTime()).as("Saved Recipe DateTime not matched with given recipe DateTime")
		  						.isEqualTo(savedRecipe.getCreationDateTime());

	  	//Make Get request with valid recipe Id and retrieve recipe
	  	RecipeEntity retRecipe = GivenValidRecipeID_WhenRequested_ThenResponseIsOK(savedRecipe.getId());
	  	
	  	//Validate the retrieved recipe instance fields are same as saved recipe fields
	  	assertThat(retRecipe.getId()).as("Retrieved Recipe Id not matched with saved recipe Id")
		     				  .isEqualTo(savedRecipe.getId());
	  	assertThat(retRecipe.getName()).as("Retrieved Recipe Name not matched with saved recipe Name")
		  					  .isEqualTo(savedRecipe.getName());
	  	assertThat(retRecipe.getCreationDateTime()).as("Retrieved Recipe DateTime not matched with saved recipe DateTime")
		  					  .isEqualTo(savedRecipe.getCreationDateTime());
	}
	
	RecipeEntity GivenValidRecipeID_WhenRequested_ThenResponseIsOK(Integer recipeId) {
		//Prepare URL parameters for GET Request with valid recipe ID
		String baseURL = "http://localhost:"+port+"/api/recipes/";
		String apiPath = baseURL+recipeId;
		
		//Make GET request to retrieve valid recipe from DB and return the response
		ResponseEntity<RecipeEntity> getResponse = restTemplate.getForEntity(apiPath, RecipeEntity.class);
		
		//Validate Http Status in Get Response is OK
		assertThat(getResponse.getStatusCode()).as("Http Status is not as expected").isEqualTo(HttpStatus.OK);
		
		//Extract Recipe Instance and return
		return getResponse.getBody();
	}
}
