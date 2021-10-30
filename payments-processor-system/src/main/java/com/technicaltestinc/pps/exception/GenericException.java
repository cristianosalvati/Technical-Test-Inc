package com.technicaltestinc.pps.exception;

import com.technicaltestinc.pps.entity.ErrorTypeEnum;

public class GenericException extends Exception{

	private ErrorTypeEnum errorType;
	
	public GenericException(ErrorTypeEnum e) {
		super();
		this.setErrorType(e);
	}
	
	public GenericException(ErrorTypeEnum e, Throwable cause) {
		super(cause);
		this.setErrorType(e);
	}

	public GenericException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setErrorType(ErrorTypeEnum.OTHER);
	}

	public GenericException(String message, Throwable cause) {
		super(message, cause);
		this.setErrorType(ErrorTypeEnum.OTHER);
	}

	public GenericException(String message) {
		super(message);
		this.setErrorType(ErrorTypeEnum.OTHER);
	}

	public GenericException(Throwable cause) {
		super(cause);
		this.setErrorType(ErrorTypeEnum.OTHER);
	}

	public String getDescription() {
		return this.getMessage();
	}
	
	public ErrorTypeEnum getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorTypeEnum errorType) {
		this.errorType = errorType;
	}

}
