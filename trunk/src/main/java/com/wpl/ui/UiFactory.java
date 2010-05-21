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
package com.wpl.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.IComponentFactory;
import com.wpl.ui.factory.components.JButtonFactory;
import com.wpl.ui.factory.components.JFrameFactory;
import com.wpl.ui.factory.components.JLabelFactory;
import com.wpl.ui.factory.components.JPanelFactory;
import com.wpl.ui.factory.components.JTextAreaFactory;
import com.wpl.ui.factory.components.JTextFieldFactory;
import com.wpl.ui.layout.BorderLayoutHandler;
import com.wpl.ui.layout.FlowLayoutHandler;
import com.wpl.ui.layout.ILayoutHandler;
import com.wpl.ui.layout.NullLayoutHandler;

/**
 * A Factory that build the UI, set it's properties using annotations.
 */
public final class UiFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(UiFactory.class);

	private final Map<Class<?>, IComponentFactory> mUiFactoryMap = new HashMap<Class<?>, IComponentFactory>();
	private final Map<Class<?>, ILayoutHandler> mLayoutHandlerMap = new HashMap<Class<?>, ILayoutHandler>();

	private String mFieldPrefix = "m";

	public UiFactory() {

		// Initialize default UI Factory.
		setUiFactory(JLabel.class, new JLabelFactory());
		setUiFactory(JButton.class, new JButtonFactory());
		setUiFactory(JPanel.class, new JPanelFactory());
		setUiFactory(JTextField.class, new JTextFieldFactory());
		setUiFactory(JTextArea.class, new JTextAreaFactory());
		setUiFactory(JFrame.class, new JFrameFactory());

		// Initialize default Layout handler.
		setLayoutHandler(BorderLayout.class, new BorderLayoutHandler());
		setLayoutHandler(NullLayout.class, new NullLayoutHandler());
		setLayoutHandler(FlowLayout.class, new FlowLayoutHandler());
	}

	public void setFieldPrefix(String fieldPrefix) {
		mFieldPrefix = fieldPrefix;
	}

	public String getFieldPrefix() {
		return mFieldPrefix;
	}

	public void setLayoutHandler(Class<?> clazz, ILayoutHandler handler) {
		mLayoutHandlerMap.put(clazz, handler);
	}

	/**
	 * Install UiFactory.
	 * 
	 * @param clazz
	 * @param uiFactory
	 */
	public void setUiFactory(Class<?> clazz, IComponentFactory uiFactory) {

		if (uiFactory == null) {

			if (clazz == JPanel.class) {
				mUiFactoryMap.put(clazz, new JPanelFactory());
				return;
			}

			if (clazz == JButton.class) {
				mUiFactoryMap.put(clazz, new JButtonFactory());
				return;
			}

			if (clazz == JLabel.class) {
				setUiFactory(JLabel.class, new JLabelFactory());
				return;
			}

			if (clazz == JTextField.class) {
				setUiFactory(JTextField.class, new JTextFieldFactory());
				return;
			}
		}

		mUiFactoryMap.put(clazz, uiFactory);
	}

	private IComponentFactory findFactory(Class<?> type) {

		if (type == Object.class || type.isPrimitive() || type.isEnum()) {
			return null;
		}

		IComponentFactory factory = this.mUiFactoryMap.get(type);

		if (factory != null) {
			return factory;
		}

		return findFactory(type.getSuperclass());
	}

	private ILayoutHandler findLayoutHandler(Class<?> type) {
		if (type == Object.class || type.isPrimitive() || type.isEnum()) {
			return findLayoutHandler(NullLayout.class);
		}

		ILayoutHandler handler = this.mLayoutHandlerMap.get(type);

		if (handler != null) {
			return handler;
		}

		return findLayoutHandler(type.getSuperclass());
	}

	private void createComponent(ComponentContext componentContext) {

		IComponentFactory factory = findFactory(componentContext.getType());

		if (factory == null) {
			return;
		}

		factory.createComponent(componentContext);

		if (componentContext.getComponent() == null) {
			return;
		}
	}

	private ComponentContext createComponentFromDeclaredField(
			FactoryContext factoryContext, Container container, Object object,
			Field f) {

		if (!Component.class.isAssignableFrom(f.getType())) {
			LOGGER.debug("ignore non-component field: {}", f.getName());
			return null;
		}

		if (Modifier.isStatic(f.getModifiers())) {
			LOGGER.debug("ignore static field: {}", f.getName());
			return null;
		}

		LOGGER.debug("building: {} of type {}", f.getName(), f.getType());

		f.setAccessible(true);

		// Initialize Component Context
		UiName uiName = f.getAnnotation(UiName.class);
		UiType uiType = f.getAnnotation(UiType.class);

		String id = uiName == null ? f.getName() : uiName.value();

		ComponentContext componentContext = factoryContext.mComponents.get(id);

		if (componentContext == null) {
			componentContext = new ComponentContext(id);
			factoryContext.mComponents.put(id, componentContext);
		}

		componentContext.setAnnotatedElement(f);
		componentContext.setContainer(container);
		componentContext.setType(uiType == null ? f.getType() : uiType.value());

		Object fieldValue = null;
		try {
			fieldValue = f.get(object);
			if (fieldValue != null) {
				componentContext.setComponent(Component.class.cast(fieldValue));
			}

			createComponent(componentContext);

			if (fieldValue == null) {
				f.set(object, componentContext.getComponent());
			}

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return componentContext;
	}

	private void createFields(FactoryContext factoryContext, Object object,
			Class<? extends Component> clazz, Container container) {

		UiLayout layout = clazz.getAnnotation(UiLayout.class);

		ILayoutHandler layoutHandler;
		if (layout == null) {
			layoutHandler = findLayoutHandler(NullLayout.class);
		} else {
			layoutHandler = findLayoutHandler(layout.value());
		}

		LOGGER.debug("layoutHandler={}", layoutHandler.getClass()
				.getSimpleName());

		Field[] fields = clazz.getDeclaredFields();

		for (Field f : fields) {

			ComponentContext componentContext = createComponentFromDeclaredField(
					factoryContext, container, object, f);

			if (componentContext == null
					|| componentContext.getComponent() == null) {
				continue;
			}

			if (layoutHandler.handleComponent(container, componentContext
					.getEnclosedComponent(), f)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Added {} ({}) into {}", new Object[] {
							f.getName(),
							componentContext.getComponent().getClass()
									.getSimpleName(),
							object.getClass().getSimpleName() });
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("{} ({}) is not added", f.getName(),
							componentContext.getComponent().getClass()
									.getSimpleName());
				}
			}

		}
	}

	public <T extends JFrame> T createFrame(Class<T> frameClazz) {

		LOGGER.debug("creating frame from: {}", frameClazz.getSimpleName());

		IComponentFactory frameFactory = findFactory(frameClazz);

		UiName name = frameClazz.getAnnotation(UiName.class);

		ComponentContext context = new ComponentContext(name == null ? "JFrame"
				: name.value());

		context.setType(frameClazz);
		context.setAnnotatedElement(frameClazz);

		frameFactory.createComponent(context);

		T frame = frameClazz.cast(context.getComponent());

		FactoryContext factoryInfo = new FactoryContext(frame);

		factoryInfo.onPreInit();

		createFields(factoryInfo, frame, frameClazz, frame.getContentPane());

		factoryInfo.onInit();

		factoryInfo.wireComponents();

		return frame;
	}

	public <T extends Container> T createContainer(Class<T> containerClass) {
		return createContainer(null, containerClass, null);
	}

	private <T extends Container> T createContainer(FactoryContext factoryInfo,
			Class<T> containerClass, Object outer) {

		LOGGER.debug("creating container from: {}", containerClass
				.getSimpleName());

		// It's impossible that JPanel.class factory is not found, so no null
		// checking is
		// required.
		IComponentFactory panelFactory = findFactory(containerClass);

		ComponentContext context = new ComponentContext(containerClass
				.getName());
		context.setType(containerClass);

		panelFactory.createComponent(context);
		T container = containerClass.cast(context.getComponent());

		// TODO: outer

		if (container == null) {
			LOGGER.warn("Failed to create container from {}", containerClass
					.getSimpleName());
			return null;
		}

		if (factoryInfo == null) {
			factoryInfo = new FactoryContext(container);
		}

		LOGGER.debug("created {} from type {}", container.getClass()
				.getSimpleName(), containerClass.getSimpleName());

		factoryInfo.onPreInit();

		createFields(factoryInfo, container, containerClass, container);

		factoryInfo.onInit();

		return container;
	}
}
