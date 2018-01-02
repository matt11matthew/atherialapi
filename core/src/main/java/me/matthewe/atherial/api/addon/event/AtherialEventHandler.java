package me.matthewe.atherial.api.addon.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Credit
 * http://importhis.appspot.com/article?id=5001
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AtherialEventHandler {
}
