package com.ssotter.sscatalog.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieledName;
	private String message;
	
	public FieldMessage() {
	}
	
	public FieldMessage(String fieledName, String message) {
		this.fieledName = fieledName;
		this.message = message;
	}

	public String getFieledName() {
		return fieledName;
	}

	public void setFieledName(String fieledName) {
		this.fieledName = fieledName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
