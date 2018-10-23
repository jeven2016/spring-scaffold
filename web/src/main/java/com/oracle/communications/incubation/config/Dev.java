package com.oracle.communications.incubation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Profile;

/**
 * Spring Profiles provide a way to segregate parts of your application configuration and make it
 * only available in certain environments. Here a dev profile is used for dev mode
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Profile("dev")
public @interface Dev {

}
