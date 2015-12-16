package com.bso.framework.parrot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将该class标志为根类
 * */
@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupperClass {
	/**
	 * 文件格式
	 * */
	Format format() default Format.TXT;
	
	/**
	 * 文件每行列数
	 * */
	int size();
}
