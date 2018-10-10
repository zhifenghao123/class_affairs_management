package com.classaffairs.framework.core.exception;

public class CoreException extends Exception {
	
	private static final long serialVersionUID = 1L;
	  
	public CoreException(String message, Exception e){
	   super(message, e);
	}
}
