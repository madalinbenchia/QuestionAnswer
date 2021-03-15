package com.StackOverflow.StackOverflow.aspects;

import java.util.List;

import com.StackOverflow.StackOverflow.exception.DifferentListSize;
import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.service.QuestionService;

public aspect QuestionListSizeCheck {

pointcut maxNumberCheck() : execution(* QuestionService.GetQuestions(..));
	
	after() returning(Object result) : maxNumberCheck() {
		Object[] arguments = thisJoinPoint.getArgs();
		System.out.println(result.toString());
		if(((List<Question>)result).size() != (int)arguments[0])
			throw new DifferentListSize();
	}
	
}
