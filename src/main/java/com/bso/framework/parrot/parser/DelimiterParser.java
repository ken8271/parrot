package com.bso.framework.parrot.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.bso.framework.parrot.handler.DefaultElementHandler;
import com.bso.framework.parrot.handler.ElementHandler;
import com.bso.framework.parrot.marshaller.ColumnMarshaller;
import com.bso.framework.parrot.marshaller.Marshaller;

public class DelimiterParser extends AbstractParser {
	
	private String delimiter;
	
	public DelimiterParser(String delimiter) {
		this(delimiter , new DefaultElementHandler());
	}
	
	public DelimiterParser(String delimiter , ElementHandler handler){
		super(new ColumnMarshaller(), handler);
		this.delimiter = delimiter;
	}
	
	public DelimiterParser(String delimiter , Marshaller marshaller , ElementHandler handler) {
		super(marshaller, handler);
		this.delimiter = delimiter;
	}
	
	@Override
	public <T> void parse(InputStream is, Class<T> clazz, int startPos, int fetchSize) throws Exception {
		BufferedReader reader = null;
		String line = null;
		int count = 0;
		try {
			handler.onStart();
			
			reader = new BufferedReader(new InputStreamReader(is));
			
			while((line = reader.readLine()) != null){
				++count;
				
				if(count < startPos + 1) continue;
				
				handler.onElementStart(line);
				
				T object = marshaller.unmarshal(clazz, line.split(delimiter));
				
				handler.onElementEnd(object);
				
				if(count >= startPos + fetchSize) break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			handler.onException(e);
		} finally {
			handler.onEnd();
			if(reader != null) reader.close();
			if(is != null) is.close();
		}
	}

}
