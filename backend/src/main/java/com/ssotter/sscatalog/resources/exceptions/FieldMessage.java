package com.ssotter.sscatalog.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
	}
	
	public FieldMessage(String fieldName, String message) {
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieledName) {
		this.fieldName = fieledName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
