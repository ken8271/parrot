package com.bso.framework.parrot.serializer;

public class StringSerializer implements Serializer {

	@Override
	public Object deserialize(String s , String parameter) {
		return s;
	}

	@Override
	public String serialize(Object object, String parameter) throws Exception {
		if(object instanceof String){
			return (String)object;
		}
		return "";
	}

	
}
