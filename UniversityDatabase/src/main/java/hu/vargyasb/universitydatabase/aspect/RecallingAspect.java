package hu.vargyasb.universitydatabase.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Aspect
@Component
public class RecallingAspect {

	private static int MAX_TRIES = 5;
	private static int WAIT_MILLIS_BETWEEN_TRIES = 500;
	
	@Pointcut("@annotation(hu.vargyasb.universitydatabase.aspect.Recall) || @within(hu.vargyasb.universitydatabase.aspect.Recall)")
	public void annotationRecall() {}

    @Around("hu.vargyasb.universitydatabase.aspect.RecallingAspect.annotationRecall()")
    public Object invoke(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        System.out.println(getClass().getSimpleName() + " -> " + thisJoinPoint);
        HttpClientErrorException ex = null;
        for (int i = 1; i <= MAX_TRIES; i++) {
            try {
                return thisJoinPoint.proceed();
            }
            catch (HttpClientErrorException e) {
            	ex = e;
                System.out.println("  Throttled during try #" + i);
                Thread.sleep(WAIT_MILLIS_BETWEEN_TRIES);
            }
        }
        //throw ex;
        return null;
    }
	
//	@Around("hu.vargyasb.universitydatabase.aspect.RecallingAspect.annotationRecall()")
//    public Object adviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object retVal = null;
//        try {
//            System.out.println(("Before executing"));
//            retVal = joinPoint.proceed();
//            System.out.println("After executing");
//        } 
//        catch(Throwable e) {
//            System.out.println("Execution error");
//            throw e;
//        }
//        return retVal;
//    }
	
	
}
