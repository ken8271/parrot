package com.bso.framework.parrot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bso.framework.parrot.serializer.Serializer;
import com.bso.framework.parrot.serializer.StringSerializer;

@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	/**
	 * 列号
	 * */
	int index() default 0;
	
	/**
	 * 序列器{@link Serializer}
	 * */
	Class<? extends Serializer> serializer() default StringSerializer.class;
	
	String title() default "";
	
	/**
	 * 额外参数
	 * */
	String parameter() default "";
	
	/**
	 * 默认值
	 * */
	String defaultValue() default "";
}
