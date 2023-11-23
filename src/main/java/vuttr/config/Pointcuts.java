package vuttr.config;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(public * *(..))")
    public void pMethod() {}


}
