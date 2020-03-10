package com.appdeveloper.app.ws.ui.model.response;

public enum ErrorMessages {
	
	MISSING_REQUIRED_FIELD("Missing requird filed, please check documentation for required fileds"),
	RECORD_ALREADY_EXITS("Record already exits"),
	INTERNAL_SERVER_ERROR("Internal server error occured"),
	NO_RECORD_FOUND("Record with the provided id is not found"),
	AUTHENTICATION_FAILED("Authentication failed"),
	COULD_NOT_UPDATE_RECORD("Could upate the record"),
	COULD_NOT_DELETE_RECORD("Could not delete the record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email addredd could not be verified");
	
	
	
	private String errorMessage;
	
	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
	

}
