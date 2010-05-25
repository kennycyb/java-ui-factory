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
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiAutoWired;
import com.wpl.ui.annotations.UiComponentOf;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiPreInit;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.IComponentFactory;
import com.wpl.ui.factory.components.FrameFactory;
import com.wpl.ui.factory.components.JButtonFactory;
import com.wpl.ui.factory.components.JCheckBoxFactory;
import com.wpl.ui.factory.components.JComboBoxFactory;
import com.wpl.ui.factory.components.JFormattedTextFieldFactory;
import com.wpl.ui.factory.components.JFrameFactory;
import com.wpl.ui.factory.components.JLabelFactory;
import com.wpl.ui.factory.components.JMenuBarFactory;
import com.wpl.ui.factory.components.JMenuFactory;
import com.wpl.ui.factory.components.JPanelFactory;
import com.wpl.ui.factory.components.JPasswordFieldFactory;
import com.wpl.ui.factory.components.JRadioButtonFactory;
import com.wpl.ui.factory.components.JTextAreaFactory;
import com.wpl.ui.factory.components.JTextFieldFactory;
import com.wpl.ui.factory.components.WindowFactory;
import com.wpl.ui.layout.BorderLayoutHandler;
import com.wpl.ui.layout.FlowLayoutHandler;
import com.wpl.ui.layout.GridLayoutHandler;
import com.wpl.ui.layout.ILayoutHandler;
import com.wpl.ui.layout.NullLayoutHandler;

/**
 * A Factory that build the UI, set it's properties using annotations.
 */
public final class UiFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(UiFactory.class);

	private static Map<Class<?>, IComponentFactory> sDefaultFactory = new HashMap<Class<?>, IComponentFactory>();

	static {
		sDefaultFactory.put(JLabel.class, new JLabelFactory());
		sDefaultFactory.put(JButton.class, new JButtonFactory());
		sDefaultFactory.put(JPanel.class, new JPanelFactory());
		sDefaultFactory.put(JTextArea.class, new JTextAreaFactory());
		sDefaultFactory.put(JFrame.class, new JFrameFactory());
		sDefaultFactory.put(Frame.class, new FrameFactory());
		sDefaultFactory.put(Window.class, new WindowFactory());
		sDefaultFactory.put(JMenuBar.class, new JMenuBarFactory());
		sDefaultFactory.put(JMenu.class, new JMenuFactory());
		sDefaultFactory.put(JCheckBox.class, new JCheckBoxFactory());
		sDefaultFactory.put(JRadioButton.class, new JRadioButtonFactory());

		sDefaultFactory.put(JComboBox.class, new JComboBoxFactory());

		sDefaultFactory.put(JTextField.class, new JTextFieldFactory());
		sDefaultFactory.put(JPasswordField.class, new JPasswordFieldFactory());
		sDefaultFactory.put(JFormattedTextField.class,
				new JFormattedTextFieldFactory());
	}

	private static Map<Class<?>, ILayoutHandler> sDefaultLayout = new HashMap<Class<?>, ILayoutHandler>();

	static {
		sDefaultLayout.put(BorderLayout.class, new BorderLayoutHandler());
		sDefaultLayout.put(NullLayout.class, new NullLayoutHandler());
		sDefaultLayout.put(FlowLayout.class, new FlowLayoutHandler());
		sDefaultLayout.put(GridLayout.class, new GridLayoutHandler());
	}

	private final Map<Class<?>, IComponentFactory> mUiFactoryMap = new HashMap<Class<?>, IComponentFactory>();
	private final Map<Class<?>, ILayoutHandler> mLayoutHandlerMap = new HashMap<Class<?>, ILayoutHandler>();

	private String mFieldPrefix = "m";

	public UiFactory() {

		// Initialize default UI Factory.
		mUiFactoryMap.putAll(sDefaultFactory);

		// Initialize default Layout handler.
		mLayoutHandlerMap.putAll(sDefaultLayout);
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
		if (type == null || type == Object.class || type.isPrimitive()
				|| type.isEnum()) {
			return findLayoutHandler(NullLayout.class);
		}

		ILayoutHandler handler = this.mLayoutHandlerMap.get(type);

		if (handler != null) {
			return handler;
		}

		return findLayoutHandler(type.getSuperclass());
	}

	private ComponentContext resolveComponentContext(
			FactoryContext factoryContext, ComponentContext componentContext) {

		UiComponentOf componentOf = componentContext.getAnnotatedElement()
				.getAnnotation(UiComponentOf.class);
		if (componentOf != null) {
			componentContext.setParentId(componentOf.value());
			factoryContext.addBindableComponent(componentContext);
		}

		// Do not resolve inner components if this is java's package.
		if (componentContext.getType().getPackage().getName()
				.startsWith("java")) {
			return componentContext;
		}

		Field[] fields = componentContext.getType().getDeclaredFields();

		for (Field f : fields) {

			if (Modifier.isStatic(f.getModifiers())) {
				LOGGER.debug("ignore static field: {}", f.getName());
				continue;
			}

			if (f.getType() == FactoryContext.class) {
				factoryContext.setFactoryContextHolder(f);
			}

			UiType uiType = f.getAnnotation(UiType.class);

			Class<?> type = uiType == null ? f.getType() : uiType.value();

			if (type == null || !Component.class.isAssignableFrom(type)) {
				LOGGER.debug("ignore non-component field: {}", f.getName());
				continue;
			}

			UiName childName = f.getAnnotation(UiName.class);

			String childId = childName == null ? f.getName() : childName
					.value();

			ComponentContext childContext = factoryContext
					.findComponentContext(childId);
			childContext.setAnnotatedElement(f);
			childContext.setDeclared(true);
			childContext.setType(type);
			resolveComponentContext(factoryContext, childContext);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("resolving {} ({})- found {} ({})", new Object[] {
						componentContext.getId(),
						componentContext.getType().getSimpleName(),
						childContext.getId(), childContext.getType() });
			}

			componentContext.addChild(childContext);
		}

		return componentContext;
	}

	private void create(FactoryContext factoryContext,
			ComponentContext componentContext, boolean isRoot) {

		if (componentContext == null || componentContext.getType() == null) {
			LOGGER
					.warn("context or type is null: {}", componentContext
							.getId());
			return;
		}

		IComponentFactory factory = findFactory(componentContext.getType());
		if (factory == null) {
			return;
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("> Creating {} ({})", new Object[] {
					componentContext.getId(), componentContext.getType() });
		}

		factory.createComponent(factoryContext, componentContext);

		if (isRoot) {
			factoryContext.setObject(componentContext.getComponent());
			if (factoryContext.getFactoryContextHolder() != null) {
				factoryContext.getFactoryContextHolder().setAccessible(true);
				try {
					factoryContext.getFactoryContextHolder().set(
							factoryContext.getObject(), factoryContext);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			factoryContext.onPreInit();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("< {} created", componentContext.getId());
		}

		Container container = null;

		if (componentContext.getComponent() instanceof JPanel) {
			container = (JPanel) componentContext.getComponent();
		} else if (componentContext.getComponent() instanceof RootPaneContainer) {
			container = ((RootPaneContainer) componentContext.getComponent())
					.getContentPane();
		} else if (componentContext.getComponent() instanceof Frame) {
			container = (Frame) componentContext.getComponent();
		} else {
			container = componentContext.getContainer();
		}

		UiLayout layout = componentContext.getAnnotatedElement().getAnnotation(
				UiLayout.class);

		ILayoutHandler layoutHandler = findLayoutHandler(layout == null ? NullLayout.class
				: layout.value());

		for (ComponentContext child : componentContext.getChildren()) {

			if (!child.isDeclared()) {
				continue;
			}

			child.setContainer(container);

			create(factoryContext, child, false);

			if (child.getAnnotatedElement() instanceof Field) {
				Field f = (Field) child.getAnnotatedElement();
				try {
					f.setAccessible(true);
					f
							.set(componentContext.getComponent(), child
									.getComponent());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (layoutHandler.handleComponent(container, child
					.getEnclosedComponent(), child.getAnnotatedElement())) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Added {} ({}) into {}", new Object[] {
							child.getId(),
							child.getComponent().getClass().getSimpleName(),
							container.getClass().getSimpleName() });
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("{} ({}) is not added", child.getId(), child
							.getType().getSimpleName());
				}
			}
		}

		if (isRoot) {
			factoryContext.onInit();
		}
	}

	private FactoryContext getFactoryContext(Class<?> mainClass) {

		FactoryContext factoryContext = new FactoryContext();

		UiAutoWired autoWired = mainClass.getAnnotation(UiAutoWired.class);

		factoryContext.setAutoWired(autoWired == null ? true : autoWired
				.value());

		Method[] methods = mainClass.getDeclaredMethods();

		for (Method m : methods) {
			if (m.getAnnotation(UiInit.class) != null) {
				factoryContext.setInitMethod(m);
				continue;
			}

			if (m.getAnnotation(UiPreInit.class) != null) {
				factoryContext.setPreInitMethod(m);
				continue;
			}

			String methodName = m.getName();

			if (methodName.startsWith("on")) {
				int action = methodName.indexOf('_');
				if (action > 0) {
					String componentName = methodName.substring(2, 3)
							.toLowerCase()
							+ methodName.substring(3, action);
					String actionName = methodName.substring(action + 1);

					ComponentContext componentContext = factoryContext
							.findComponentContext(componentName);
					componentContext.addActionListener(actionName, m);

					if (LOGGER.isDebugEnabled()) {
						LOGGER
								.debug(
										"Found autowired listener: {}, registered for {} on {}",
										new Object[] { methodName,
												componentName, actionName });
					}
				}
			}
		}

		return factoryContext;
	}

	public <T extends Frame> T createFrame(Class<T> frameClass) {

		LOGGER.debug("Creating {}", frameClass.getSimpleName());

		FactoryContext factoryContext = getFactoryContext(frameClass);

		UiName name = frameClass.getAnnotation(UiName.class);
		UiType cType = frameClass.getAnnotation(UiType.class);

		String id;
		if (name == null) {
			id = frameClass.getSimpleName().substring(0, 1).toLowerCase()
					+ frameClass.getSimpleName().substring(1);
		} else {
			id = name.value();
		}

		ComponentContext componentContext = factoryContext
				.findComponentContext(id);
		componentContext.setRoot(true);
		componentContext.setAnnotatedElement(frameClass);
		componentContext.setType(cType == null ? frameClass : cType.value());

		resolveComponentContext(factoryContext, componentContext);

		create(factoryContext, componentContext, true);

		T frame = frameClass.cast(componentContext.getComponent());

		if (frameClass.getAnnotation(UiSize.class) == null) {
			frame.pack();
		}

		return frame;
	}

	public <T extends Component> T createComponent(Class<T> componentClass) {
		LOGGER.debug("Creating {}", componentClass.getSimpleName());

		FactoryContext factoryContext = getFactoryContext(componentClass);
		ComponentContext componentContext = new ComponentContext();

		UiName name = componentClass.getAnnotation(UiName.class);
		UiType cType = componentClass.getAnnotation(UiType.class);

		componentContext.setId(name == null ? componentClass.getSimpleName()
				: name.value());
		componentContext.setAnnotatedElement(componentClass);
		componentContext
				.setType(cType == null ? componentClass : cType.value());

		resolveComponentContext(factoryContext, componentContext);

		create(factoryContext, componentContext, true);

		T component = componentClass.cast(componentContext.getComponent());

		return component;

	}
}
