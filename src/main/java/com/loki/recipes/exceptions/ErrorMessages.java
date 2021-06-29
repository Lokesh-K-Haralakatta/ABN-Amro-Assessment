package com.loki.recipes.exceptions;

//Class to contain customized error messages
public class ErrorMessages {
	public static final String RECIPE_NOT_FOUND_MSG = "Requested recipe not found in DB";
	public static final String BAD_REQUEST_MSG = "Bad Request, check request body / parameter type and value";
	public static final String INTERNAL_SERVER_ERR_MSG = "Unknown error occurred, check the logs for more details";
}
