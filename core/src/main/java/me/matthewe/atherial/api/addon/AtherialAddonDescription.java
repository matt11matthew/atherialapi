package me.matthewe.atherial.api.addon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Matthew E on 12/30/2017.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AtherialAddonDescription {

    String name();

    String version();

    String description() default "An addon";

    String[] authors() default {};
}
