package com.StackOverflow.StackOverflow.aspects;
import java.util.logging.*;

public aspect ErrorLoggingAspect {

	pointcut publicMethodExecuted(): execution(public * *(..));

	after() throwing (Exception e): publicMethodExecuted() {
		Logger logger = Logger.getGlobal();
		
		String theError = "Error: " + thisJoinPoint.getSignature() + " - " + e.getMessage() + ". \n";
		logger.log(Level.SEVERE, theError);
	    System.out.printf(theError);

	    Object[] arguments = thisJoinPoint.getArgs();
	    for (int i =0; i < arguments.length; i++){
	        Object argument = arguments[i];
	        if (argument != null){
	        	String theErrorInfo = "With argument of type " + argument.getClass().toString() + " and value " + argument + ". \n";
	        	logger.log(Level.SEVERE, theErrorInfo);
	            System.out.printf(theErrorInfo);
	        }
	    }
	    
	}
	
}
