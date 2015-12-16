package com.bso.framework.parrot.parser;

import java.io.InputStream;

import com.bso.framework.parrot.handler.ElementHandler;
import com.bso.framework.parrot.marshaller.Marshaller;

public abstract class AbstractParser implements Parser {
	
	private static final int MAX_SIZE = 1000;

	protected Marshaller marshaller;

	protected ElementHandler handler;

	public AbstractParser(Marshaller marshaller, ElementHandler handler) {
		this.marshaller = marshaller;
		this.handler = handler;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public ElementHandler getHandler() {
		return handler;
	}

	public void setHandler(ElementHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public <T> void parse(InputStream is, Class<T> clazz) throws Exception {
		parse(is, clazz, 0);
	}
	
	@Override
	public <T> void parse(InputStream is, Class<T> clazz, int startPos)
			throws Exception {
		parse(is, clazz, startPos, MAX_SIZE);
	}
	
}
