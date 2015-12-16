package com.bso.framework.parrot.serializer;

import java.util.Date;

import com.bso.commons.utils.DateUtils;

public class DateSerializer implements Serializer {

	@Override
	public Object deserialize(String s, String parameter) throws Exception {
		return DateUtils.parse(parameter, s);
	}

	@Override
	public String serialize(Object object, String parameter) throws Exception {
		if(object instanceof Date){			
			return DateUtils.formatDateTime(parameter, (Date)object);
		}
		
		return "";
	}

}
