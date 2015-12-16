package com.bso.framework.parrot.serializer;

public interface Serializer {
	public Object deserialize(String s , String parameter) throws Exception;
	public String serialize(Object object , String parameter) throws Exception;
}
