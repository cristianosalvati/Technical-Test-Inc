package com.technicaltestinc.pps.exception;

import com.technicaltestinc.pps.entity.ErrorTypeEnum;

public class NetworkException extends GenericException{

	public NetworkException() {
		super(ErrorTypeEnum.NETWORK);
	}
	
	public NetworkException(Throwable t) {
		super(ErrorTypeEnum.NETWORK, t);
	}

}