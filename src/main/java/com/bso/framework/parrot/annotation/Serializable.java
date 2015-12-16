package com.bso.framework.parrot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标志该类class是否可以被反序列化
 * 即标志是否可以将文件属性反序列化为对象
 * */
@Documented
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Serializable {
	
}
