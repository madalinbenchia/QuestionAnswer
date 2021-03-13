package com.StackOverflow.StackOverflow.aspects;

public aspect ErrorLoggingAspect {

	pointcut publicMethodExecuted(): execution(public * *(..));

	after() throwing (Exception e): publicMethodExecuted() {
	    System.out.printf("Error: %s %s. \n", thisJoinPoint.getSignature(), e.getMessage());

	    Object[] arguments = thisJoinPoint.getArgs();
	    for (int i =0; i < arguments.length; i++){
	        Object argument = arguments[i];
	        if (argument != null){
	            System.out.printf("With argument of type %s and value %s. \n", argument.getClass().toString(), argument);
	        }
	    }
	    
	}
	
}
