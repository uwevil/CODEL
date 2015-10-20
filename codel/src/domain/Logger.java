package domain;

import org.aspectj.lang.JoinPoint;

public class Logger {
	
	public void log(final JoinPoint joinPoint){
		System.out.println("     ***************** ASPECT:        " + joinPoint.getSignature().toString());
	}

}
