package com.StackOverflow.StackOverflow.exception;

public class DifferentListSize extends RuntimeException {
	public DifferentListSize() {super("Result list size is different from the maxNmber provided");}

}
