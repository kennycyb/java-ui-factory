/*
 * Copyright 2010 Kenny Chong (wongpeiling.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wpl.ui.factory.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiLocation;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiScrollable;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.enums.ScrollBarPolicy;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.IComponentFactory;
import com.wpl.ui.factory.UiAnnotationHandler;

public abstract class ComponentFactory implements IComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ComponentFactory.class);

	private final Map<Class<? extends Annotation>, Method> mAnnotationHandlerMap = new HashMap<Class<? extends Annotation>, Method>();

	public ComponentFactory() {
		findAllMethods(this.getClass());
	}

	private void findAllMethods(Class<?> clazz) {

		if (clazz == null || clazz == Object.class) {
			return;
		}

		findAllMethods(clazz.getSuperclass());
		for (Class<?> i : clazz.getInterfaces()) {
			findAllMethods(i);
		}

		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			UiAnnotationHandler annotated = m
					.getAnnotation(UiAnnotationHandler.class);
			if (annotated != null) {
				mAnnotationHandlerMap.put(annotated.value(), m);
			}
		}
	}

	// @Override
	// public Component createComponent(ComponentContext context,
	// Class<? extends Component> clazz, Annotation[] annotations,
	// Object outer) {
	//
	// if (outer == null) {
	// return createComponent(clazz, annotations);
	// }
	//
	// try {
	// Constructor<? extends Component> innerConstructor = clazz
	// .getConstructor(outer.getClass());
	// Component innerObject = innerConstructor.newInstance(outer);
	//
	// return init(innerObject, annotations);
	// } catch (SecurityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return null;
	// }

	abstract protected Class<?> defaultType();

	@Override
	public void createComponent(FactoryContext factory, ComponentContext context) {

		if (context == null) {
			return;
		}

		if (context.getType() == null) {
			context.setType(defaultType());
		}

		// TODO: handle inner class

		try {
			this.createInstance(context);

			if (context.isRoot() && factory.getObject() == null) {
				factory.setObject(context.getComponent());
			}

			this.init(factory, context);
		} catch (Throwable t) {
			LOGGER.error("Failed to create component", t);
		}
	}

	private void createInstance(ComponentContext context)
			throws InstantiationException, IllegalAccessException {
		context.setComponent(Component.class.cast(context.getType()
				.newInstance()));
	}

	protected void init(FactoryContext factory, ComponentContext context) {

		if (context.getComponent() == null) {
			LOGGER.debug("init failed - component is null");
			return;
		}

		if (context.getAnnotatedElement() == null) {
			LOGGER.debug("init failed - annotated element is null");
			return;
		}

		// Build the current component.

		for (Annotation annotate : context.getAnnotatedElement()
				.getAnnotations()) {

			Method handler = mAnnotationHandlerMap.get(annotate
					.annotationType());
			if (handler == null) {
				LOGGER.debug("No handler for {}", annotate.annotationType());
				continue;
			}

			try {
				handler.invoke(this, factory, context, context.getComponent(),
						annotate);
			} catch (IllegalArgumentException e1) {

				LOGGER.warn("IllegalArgument: {}({}, {})", new Object[] {
						handler.getName(),
						context.getComponent().getClass().getSimpleName(),
						annotate.annotationType().getSimpleName() });

			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		this.wireComponent(factory, context);
	}

	@Override
	public void wireComponent(FactoryContext factory, ComponentContext context) {
	}

	// ~ UiAnnotationHandlers --------------------------------------------------

	@UiAnnotationHandler(UiBorderLayoutConstraint.class)
	protected void handleUiBorderLayoutConstraint(FactoryContext factory,
			ComponentContext context, Component component,
			UiBorderLayoutConstraint annotate) {

	}

	@UiAnnotationHandler(UiSize.class)
	protected void handleUiSize(FactoryContext factory,
			ComponentContext context, Component component, UiSize annotate) {
		component.setSize(annotate.width(), annotate.height());
		component.setPreferredSize(new Dimension(annotate.width(), annotate
				.height()));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(Component){}.setSize(width={}, height={})",
					new Object[] { context.getId(), annotate.width(),
							annotate.height() });
		}
	}

	@UiAnnotationHandler(UiLocation.class)
	protected void handleUiLocation(FactoryContext factory,
			ComponentContext context, Component component, UiLocation location) {
		component.setLocation(location.x(), location.y());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(Component){}.setLocation(x={}, y={})", new Object[] {
					context.getId(), location.x(), location.y() });
		}
	}

	@UiAnnotationHandler(UiFont.class)
	protected void handleUiFont(FactoryContext factory,
			ComponentContext context, Component component, UiFont font) {

		component.setFont(new Font(font.name(),
				font.style().getSwingConstant(), font.size()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(Component){}.setFont(name={}, style={}, size={})",
					new Object[] { context.getId(), font.name(), font.style(),
							font.size() });
		}
	}

	@UiAnnotationHandler(UiName.class)
	protected void handleUiName(FactoryContext factory,
			ComponentContext context, Component component, UiName name) {
		component.setName(name.value());
	}

	private int getScrollBarPolicyValue(boolean isHorizontal,
			ScrollBarPolicy policy) {

		if (isHorizontal) {
			switch (policy) {
			case ALWAYS:
				return JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS;

			case AS_NEEDED:
				return JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;

			case NEVER:
				return JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;

			default:
				return JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
			}
		}

		switch (policy) {
		case ALWAYS:
			return JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;

		case AS_NEEDED:
			return JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;

		case NEVER:
			return JScrollPane.VERTICAL_SCROLLBAR_NEVER;

		default:
			return JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
		}

	}

	@UiAnnotationHandler(UiScrollable.class)
	protected void handleUiScrollable(FactoryContext factory,
			ComponentContext context, Component component, UiScrollable annotate) {
		JScrollPane scroll = new JScrollPane(component);

		scroll.setHorizontalScrollBarPolicy(getScrollBarPolicyValue(true,
				annotate.horizontal()));
		scroll.setVerticalScrollBarPolicy(getScrollBarPolicyValue(false,
				annotate.vertical()));
		scroll.setAutoscrolls(annotate.autoScroll());

		context.setEnclosedComponent(scroll);

		LOGGER.debug("component={}, enclosing {} with JScrollPane", context
				.getId(), component.getClass().getSimpleName());
	}
}
