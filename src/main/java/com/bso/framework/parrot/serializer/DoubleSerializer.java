package com.bso.framework.parrot.serializer;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

public class DoubleSerializer implements Serializer {

	@Override
	public Object deserialize(String s, String parameter) throws Exception {
		return Double.valueOf(s);
	}

	@Override
	public String serialize(Object object, String parameter) throws Exception {
		if(object instanceof Double){
			if(StringUtils.isNotBlank(parameter))
				return new DecimalFormat(parameter).format(object);
			else 
				return Double.toString((Double)object);
		}
		return "";
	}
	
}
