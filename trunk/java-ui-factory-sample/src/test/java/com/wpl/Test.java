package com.wpl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.wpl.ui.ext.GridView;
import com.wpl.ui.samples.model.Person;

public class Test {

	private GridView<Person> gridview;

	@org.junit.Test
	public void genericTest() throws SecurityException, NoSuchFieldException,
			ClassNotFoundException {

		Field field = this.getClass().getDeclaredField("gridview");

		ParameterizedType type = (ParameterizedType) field.getGenericType();

		Type actualType = type.getActualTypeArguments()[0];

		System.out.println(actualType);

		Class<?> cls = (Class<?>) actualType;

		Field[] fields = cls.getDeclaredFields();

		for (Field f : fields) {
			System.out.println(f.getName());
		}
	}
}
