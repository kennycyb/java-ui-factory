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
package com.wpl.ui.factory.components.awt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.enums.ScrollBarPolicy;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.IComponentFactory;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.listeners.MethodListenerProxy;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class ComponentFactory implements IComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ComponentFactory.class);

	private final Map<Class<? extends Annotation>, Method> mAnnotationHandlerMap = new HashMap<Class<? extends Annotation>, Method>();

	public ComponentFactory() {
		findAllMethods(this.getClass());
	}

	private void findAllMethods(final Class<?> clazz) {

		if (clazz == null || clazz == Object.class) {
			return;
		}

		findAllMethods(clazz.getSuperclass());
		for (final Class<?> i : clazz.getInterfaces()) {
			findAllMethods(i);
		}

		final Method[] methods = clazz.getDeclaredMethods();
		for (final Method m : methods) {
			final UiAnnotationHandler annotated = m
					.getAnnotation(UiAnnotationHandler.class);
			if (annotated != null) {
				mAnnotationHandlerMap.put(annotated.value(), m);
			}
		}
	}

	public void createInstance(final ComponentContext context) throws Exception {

		Object instance = null;

		if (context.getParentContext() != null
				&& context.getType().getDeclaringClass() != null
				&& !Modifier.isStatic(context.getType().getModifiers())) {

			final Class<?> innerClass = context.getType();
			final Class<?> outerClass = context.getParentContext().getType();

			final Constructor<?> innerClassConstructor = innerClass
					.getDeclaredConstructor(outerClass);

			innerClassConstructor.setAccessible(true);

			instance = innerClassConstructor.newInstance(context
					.getParentContext().getComponent());
		} else {

			LOGGER.debug("{}|creating from {}", context.getId(), context
					.getType());

			instance = context.getType().newInstance();
		}

		context.setComponent(Component.class.cast(instance));
	}

	public void initialize(final ComponentContext context) {

		if (context.getComponent() == null) {
			LOGGER.debug("init failed - component is null");
			return;
		}

		if (context.getAnnotatedElement() == null) {
			LOGGER.debug("init failed - annotated element is null");
			return;
		}

		LOGGER.debug("{}|init begin", context.getId());

		// Build the current component.

		if (context.getParentContext() != null) {
			handleAnnotations(context, context.getType());
		}

		handleAnnotations(context, context.getAnnotatedElement());

		if (context.isAutoWired()) {
			LOGGER.debug("{}|init auto-wiring", context.getId());
			wireComponent(context);
		} else {
			LOGGER.debug("{}|init auto-wiring disabled");
		}

		LOGGER.debug("{}|init done", context.getId());
	}

	private void handleAnnotations(final ComponentContext context,
			final AnnotatedElement annotatedElement) {

		for (final Annotation annotate : annotatedElement.getAnnotations()) {

			final Method handler = mAnnotationHandlerMap.get(annotate
					.annotationType());
			if (handler == null) {
				LOGGER.debug("No handler for {}", annotate.annotationType());
				continue;
			}

			try {
				handler.invoke(this, context, context.getComponent(), annotate);
			} catch (final IllegalArgumentException e1) {

				LOGGER.warn("IllegalArgument: {}({}, {})", new Object[] {
						handler.getName(),
						context.getComponent().getClass().getSimpleName(),
						annotate.annotationType().getSimpleName() });

			} catch (final IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (final InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	protected void wireComponent(final ComponentContext context) {
		LOGGER.debug("{}|listeners={}", context.getId(), context
				.getActionListeners().size());

		final Component component = context.getComponent();

		final MethodListenerProxy<MouseMotionListener> mouseMotionListenerProxy = new MethodListenerProxy<MouseMotionListener>(
				MouseMotionListener.class, context.getActionListeners());

		if (mouseMotionListenerProxy.hasListeningMethod()) {
			component.addMouseMotionListener(mouseMotionListenerProxy
					.getProxy());
		}

		final MethodListenerProxy<MouseListener> mouseListenerProxy = new MethodListenerProxy<MouseListener>(
				MouseListener.class, context.getActionListeners());

		if (mouseListenerProxy.hasListeningMethod()) {
			component.addMouseListener(mouseListenerProxy.getProxy());
		}

		final MethodListenerProxy<MouseWheelListener> mouseWheelListenerProxy = new MethodListenerProxy<MouseWheelListener>(
				MouseWheelListener.class, context.getActionListeners());

		if (mouseWheelListenerProxy.hasListeningMethod()) {
			component.addMouseWheelListener(mouseWheelListenerProxy.getProxy());
		}

		final MethodListenerProxy<KeyListener> keyListenerProxy = new MethodListenerProxy<KeyListener>(
				KeyListener.class, context.getActionListeners());

		if (keyListenerProxy.hasListeningMethod()) {
			component.addKeyListener(keyListenerProxy.getProxy());
		}

		final MethodListenerProxy<FocusListener> focusListenerProxy = new MethodListenerProxy<FocusListener>(
				FocusListener.class, context.getActionListeners());

		if (focusListenerProxy.hasListeningMethod()) {
			component.addFocusListener(focusListenerProxy.getProxy());
		}
	}

	// ~ UiAnnotationHandlers --------------------------------------------------

	@UiAnnotationHandler(UiType.class)
	protected void handleUiAnnotation(final ComponentContext context,
			final Component component, final UiType annotate) {
		LOGGER.debug("{}|is {}", context.getId(), context.getType());
	}

	@UiAnnotationHandler(UiBorderLayoutConstraint.class)
	protected void handleUiBorderLayoutConstraint(
			final ComponentContext context, final Component component,
			final UiBorderLayoutConstraint annotate) {

		LOGGER.debug("{}|BorderLayoutConstraint.{}", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiSize.class)
	protected void handleUiSize(final ComponentContext context,
			final Component component, final UiSize annotate) {
		component.setSize(annotate.width(), annotate.height());
		component.setPreferredSize(new Dimension(annotate.width(), annotate
				.height()));

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Component.setSize(width={}, height={})",
					new Object[] { context.getId(), annotate.width(),
							annotate.height() });
		}

		context.setPack(false);
	}

	@UiAnnotationHandler(UiLocation.class)
	protected void handleUiLocation(final ComponentContext context,
			final Component component, final UiLocation location) {
		component.setLocation(location.x(), location.y());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Component.setLocation(x={}, y={})", new Object[] {
					context.getId(), location.x(), location.y() });
		}
	}

	@UiAnnotationHandler(UiFont.class)
	protected void handleUiFont(final ComponentContext context,
			final Component component, final UiFont font) {

		component.setFont(new Font(font.name(),
				font.style().getSwingConstant(), font.size()));
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Component.setFont(name={}, style={}, size={})",
					new Object[] { context.getId(), font.name(), font.style(),
							font.size() });
		}
	}

	@UiAnnotationHandler(UiName.class)
	protected void handleUiName(final ComponentContext context,
			final Component component, final UiName name) {
		component.setName(name.value());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Component.setName(\"{}\")", context.getId(), name
					.value());
		}
	}

	private int getScrollBarPolicyValue(final boolean isHorizontal,
			final ScrollBarPolicy policy) {

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
	protected void handleUiScrollable(final ComponentContext context,
			final Component component, final UiScrollable annotate) {
		final JScrollPane scroll = new JScrollPane(component);

		scroll.setHorizontalScrollBarPolicy(getScrollBarPolicyValue(true,
				annotate.horizontal()));
		scroll.setVerticalScrollBarPolicy(getScrollBarPolicyValue(false,
				annotate.vertical()));
		scroll.setAutoscrolls(annotate.autoScroll());

		context.setEnclosedComponent(scroll);

		LOGGER.debug("{}|enclosing with JScrollPane", context.getId(),
				component.getClass().getSimpleName());
	}
}
