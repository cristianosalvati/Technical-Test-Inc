package com.technicaltestinc.pps.exception;

import com.technicaltestinc.pps.entity.ErrorTypeEnum;

public class DatabaseException extends GenericException{

	public DatabaseException() {
		super(ErrorTypeEnum.DATABASE);
	}
	
	public DatabaseException(Throwable t) {
		super(ErrorTypeEnum.DATABASE, t);
	}

}
