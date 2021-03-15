package com.StackOverflow.StackOverflow.aspects;

import com.StackOverflow.StackOverflow.exception.post.NegativeArgumentException;
import com.StackOverflow.StackOverflow.service.QuestionService;
import com.StackOverflow.StackOverflow.service.UserService;
public aspect CheckNegativeArgumentsAspect {

	pointcut maxNumberCheck() : execution(* QuestionService.GetQuestions(..)) || execution(* UserService.GetUserById(..));
	
	before() : maxNumberCheck() {
		Object[] arguments = thisJoinPoint.getArgs();
		if((int)arguments[0] < 0)
			throw  new NegativeArgumentException();
	}
}
