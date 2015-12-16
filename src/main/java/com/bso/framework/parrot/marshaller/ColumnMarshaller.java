package com.bso.framework.parrot.marshaller;

import java.lang.reflect.Field;
import java.util.Date;

import com.bso.framework.parrot.annotation.Column;
import com.bso.framework.parrot.annotation.Serializable;
import com.bso.framework.parrot.annotation.SupperClass;
import com.bso.framework.parrot.marshaller.Marshaller;
import com.bso.framework.parrot.serializer.Serializer;

public class ColumnMarshaller implements Marshaller {

	@Override
	public <T> T unmarshal(Class<T> clazz, String[] props) throws Exception{
		T target = clazz.newInstance();
		
		Field[] fields = clazz.getDeclaredFields();

		if(fields != null && fields.length != 0){
			for(Field field : fields){
				field.setAccessible(true);
				
				if(!isPrimitive(field)) field.set(target, unmarshal(field.getType(), props));
					
				if(field.isAnnotationPresent(Column.class)){
					Column col = field.getAnnotation(Column.class);
					
					int index = col.index(); 
					Class<? extends Serializer> serializer = col.serializer();
					String parameter = col.parameter();

					if(index < props.length){
						Object prop = serializer.newInstance().deserialize(props[index] , parameter);
						
						if(prop != null)
							field.set(target, prop);						
					}
				}
			}
		}
		
		return target;
	}
	
	private boolean isPrimitive(Field field){
		if(field.getType().isPrimitive()) return true;
		
		if(String.class.equals(field.getType())
				|| Integer.class.equals(field.getType())
				|| Double.class.equals(field.getType())
				|| Date.class.equals(field.getType())){
			return true;
		}else {
			return false;
		}
	}
	
	private void marshal(Object object , String[] props) throws Exception{
		Class<? extends Object> clazz = object.getClass();
		
		if(clazz.isAnnotationPresent(Serializable.class)){
			Field[] fields = clazz.getDeclaredFields();
			
			if(fields != null && fields.length != 0){
				for(Field field : fields){
					field.setAccessible(true);
					
					if(!isPrimitive(field)) marshal(field.get(object), props);
					
					if(field.isAnnotationPresent(Column.class)){
						Column c = field.getAnnotation(Column.class);
						
						if(c.index() < props.length){
							props[c.index()] = c.serializer().newInstance().serialize(field.get(object), c.parameter());
						}
					}
				}
			}
		}
	}

	@Override
	public String[] marshal(Object object) throws Exception {
		Class<? extends Object> clazz = object.getClass();
		
		if(clazz.isAnnotationPresent(SupperClass.class)){
			SupperClass sc = clazz.getAnnotation(SupperClass.class);
			
			String[] props = new String[sc.size()];
			
			marshal(object, props);
			
			return props;
		}
		
		return null;
	}
}
