package org.example.httpclient;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RestTemplateApiClientsRegistrar.class)
public @interface EnableRestTemplateApiClients {
    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @EnableHttpApiClients("org.my.pkg")} instead of
     * {@code @EnableHttpApiClients(basePackages="org.my.pkg")}.
     */
    String[] value() default {};

    String[] basePackages() default {};

    String restTemplateRef() default "restTemplate";
}
