package com.StackOverflow.StackOverflow.aspects;

import com.StackOverflow.StackOverflow.exception.post.NegativeArgumentException;
import com.StackOverflow.StackOverflow.service.QuestionService;
import com.StackOverflow.StackOverflow.service.UserService;
import com.StackOverflow.StackOverflow.service.CommentService;
import com.StackOverflow.StackOverflow.service.AnswerService;

public aspect CheckNegativeArgumentsAspect {

	pointcut maxNumberCheck() : execution(* QuestionService.GetQuestions(..)) ||  
								execution(* QuestionService.GetQuestionWithAnswers(..)) ||
								execution(* QuestionService.DeleteQuestion(..)) ||
								execution(* CommentService.GetCommentById(..)) ||
								execution(* CommentService.GetAllCommentsForAnAswer(..)) ||
								execution(* CommentService.DeleteComment(..)) ||
								execution(* AnswerService.GetAnswers(..)) ||
								execution(* AnswerService.GetAnswerWithComments(..)) ||
								execution(* UserService.GetUserById(..));
	
	before() : maxNumberCheck() {
		Object[] arguments = thisJoinPoint.getArgs();
		if((int)arguments[0] < 0)
			throw  new NegativeArgumentException();
	}
}
