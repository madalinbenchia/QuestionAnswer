package com.StackOverflow.StackOverflow.aspects;
import java.util.logging.*;

public aspect LoggingAspect {
	
	Logger logger = Logger.getGlobal();
	
	pointcut methodExecuted() : call(public * com.StackOverflow.StackOverflow.*.*.*(..));
	
	before() : methodExecuted() {
		String loggedInfo = "Entering " + thisJoinPoint.getSignature() + "...";
		logger.info(loggedInfo);
	}
	
	after() returning(Object result) : methodExecuted() {
		String loggedInfo = "Exited " + thisJoinPoint.getSignature() + " with returned value: " + result;
		logger.info(loggedInfo);
	}
}
