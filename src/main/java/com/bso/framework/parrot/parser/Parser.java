package com.bso.framework.parrot.parser;

import java.io.InputStream;

public interface Parser {
	public <T> void parse(InputStream is , Class<T> clazz) throws Exception;
	public <T> void parse(InputStream is , Class<T> clazz , int startPos) throws Exception;
	public <T> void parse(InputStream is , Class<T> clazz , int startPos , int fetchSize) throws Exception;
}
