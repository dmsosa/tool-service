package vuttr.controller;

import org.springframework.security.test.context.support.WithSecurityContext;
import vuttr.WithMyUserFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMyUserFactory.class)
public @interface WithMyUser {
    String username() default "bobby";
    String name() default "Bob";
}
