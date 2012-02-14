package com.wpl.ui.ext.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.ext.gridview.GridView;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.impl.components.awt.ComponentFactory;

public final class GridViewFactory extends JxComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(GridViewFactory.class);

	@Override
	public void initialize(ComponentContext context) {
		super.initialize(context);

		GridView<?> gridview = (GridView<?>) context.getJxComponent();

		Field field = (Field) context.getAnnotatedElement();

		ParameterizedType type = (ParameterizedType) field.getGenericType();

		Type actualType = type.getActualTypeArguments()[0];

		Class<?> cls = (Class<?>) actualType;

		Method[] methods = cls.getMethods();

		List<String> properties = new ArrayList<String>();

		for (Method method : methods) {

			// Only if it's a getter

			if (Modifier.isStatic(method.getModifiers())) {
				// Ignore static method
				continue;
			}

			if (method.getParameterTypes().length != 0) {
				// Ignore method with parameter(s)
				continue;
			}

			if (method.getName().startsWith("is")
					&& method.getName().length() > 2) {

				// Is a boolean getter

				String name = method.getName().substring(2, 2).toUpperCase()
						+ method.getName().substring(3);
				properties.add(name);

				continue;
			}
			
			if (method.getName().equals("getClass"))
				continue;

			if (method.getName().startsWith("get")
					&& method.getName().length() > 3) {

				// Is a getter

				String name = method.getName().substring(3, 3).toUpperCase()
						+ method.getName().substring(4);
				properties.add(name);

				continue;
			}

			// Ignore this method
		}

		gridview.setProperties(properties);
		
		context.setComponent(gridview.getComponent());
		context.setEnclosedComponent(new JScrollPane(gridview.getComponent()));
	}
}
