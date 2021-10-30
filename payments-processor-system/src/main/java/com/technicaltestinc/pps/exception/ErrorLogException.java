package com.technicaltestinc.pps.exception;

import com.technicaltestinc.pps.entity.ErrorTypeEnum;

public class ErrorLogException extends GenericException{

	public ErrorLogException() {
		super(ErrorTypeEnum.NETWORK);
	}
	
	public ErrorLogException(Throwable t) {
		super(ErrorTypeEnum.NETWORK, t);
	}

}
