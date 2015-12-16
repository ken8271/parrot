package com.bso.framework.parrot.handler;

public interface ElementHandler {

	public void onStart();
	
	public void onElementStart(Object object);
	
	public void onElementEnd(Object object);
	
	public void onEnd();
	
	public void onException(Exception e);
}
