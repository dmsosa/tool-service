package vuttr.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {
    @After("logMethods()")
    public void stuff(JoinPoint jp) throws Throwable {

        Object[] args = jp.getArgs();

        System.out.println(jp.getStaticPart().getSignature());
        System.out.println(jp.getSignature());
        System.out.println(jp.getKind());
        System.out.println(jp.toLongString());
        System.out.println(jp.getSourceLocation());
        System.out.println(jp.getTarget());
        System.out.println(jp.getThis());
        System.out.println(jp.toShortString());


        Arrays.stream(args).forEach(x -> System.out.println(x));
    }
    @Pointcut("execution(public * vuttr.service.ToolService.getToolById(*))")
    public void logMethods() {}

}
