package com.bso.framework.parrot.marshaller;

public interface Marshaller {
	/**
	 * @param Class<T> clazz
	 * @param String[] props
	 * @return T
	 * */
	public <T> T unmarshal(Class<T> clazz , String[] props) throws Exception;
	
	/**
	 * @param Object object
	 * @return String[] 
	 * */
	public String[] marshal(Object object) throws Exception;
}
