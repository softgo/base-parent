package com.application.base.mongo.morphia.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Inherited;

/**
 * 
 * 用来标注多个数据源的.
 * 
 * @author admin
 *
 */
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataStore {

	String tagValue();
	
	String mongoDBName() default "";
	
}
