package com.StackOverflow.StackOverflow.aspects;

public aspect JoinPointTraceAspect {

	private int _callDept = -1;
	
	pointcut tracePoints() : !within(JoinPointTraceAspect);
	
	before() : tracePoints() {
		_callDept++;
		print("Before", thisJoinPoint);
	}
	
	after() : tracePoints() {
		print("After", thisJoinPoint);
		_callDept--;
	}
	
	private void print(String prefix, Object message) {
		for(int i = 0, spaces=_callDept*2; i < spaces; i++) {
			//System.out.print(" ");
		}
		//System.out.println(prefix + ": " + message);
	}
}
