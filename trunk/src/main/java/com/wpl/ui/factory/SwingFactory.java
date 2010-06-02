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
package com.wpl.ui.factory;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiAddComponentTo;
import com.wpl.ui.annotations.UiComponentOf;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiPreInit;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.annotations.frame.UiFrameMenu;
import com.wpl.ui.events.EventHandler;
import com.wpl.ui.factory.components.DialogFactory;
import com.wpl.ui.factory.components.FileDialogFactory;
import com.wpl.ui.factory.components.FrameFactory;
import com.wpl.ui.factory.components.JButtonFactory;
import com.wpl.ui.factory.components.JCheckBoxFactory;
import com.wpl.ui.factory.components.JComboBoxFactory;
import com.wpl.ui.factory.components.JDialogFactory;
import com.wpl.ui.factory.components.JFormattedTextFieldFactory;
import com.wpl.ui.factory.components.JFrameFactory;
import com.wpl.ui.factory.components.JLabelFactory;
import com.wpl.ui.factory.components.JListFactory;
import com.wpl.ui.factory.components.JMenuBarFactory;
import com.wpl.ui.factory.components.JMenuFactory;
import com.wpl.ui.factory.components.JPanelFactory;
import com.wpl.ui.factory.components.JPasswordFieldFactory;
import com.wpl.ui.factory.components.JRadioButtonFactory;
import com.wpl.ui.factory.components.JTabbedPaneFactory;
import com.wpl.ui.factory.components.JTextAreaFactory;
import com.wpl.ui.factory.components.JTextFieldFactory;
import com.wpl.ui.factory.components.TextAreaFactory;
import com.wpl.ui.factory.components.TextComponentFactory;
import com.wpl.ui.factory.components.TextFieldFactory;
import com.wpl.ui.factory.components.WindowFactory;
import com.wpl.ui.layout.handlers.BorderLayoutHandler;
import com.wpl.ui.layout.handlers.FlowLayoutHandler;
import com.wpl.ui.layout.handlers.GridLayoutHandler;
import com.wpl.ui.layout.handlers.ILayoutHandler;
import com.wpl.ui.layout.handlers.NullLayoutHandler;
import com.wpl.ui.layout.handlers.SpringLayoutHandler;
import com.wpl.ui.layout.handlers.VerticalFlowLayoutHandler;
import com.wpl.ui.layout.managers.NullLayout;
import com.wpl.ui.layout.managers.VerticalFlowLayout;
import com.wpl.ui.listeners.MethodListener;

/**
 * 
 * @since 1.0
 */
public class SwingFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(SwingFactory.class);

	public static <T extends Component> T create(final Class<T> componentClass) {
		return sInstance.createComponent(componentClass);
	}

	private static Map<Class<?>, IComponentFactory> sDefaultFactory = new HashMap<Class<?>, IComponentFactory>();
	private static Map<Class<?>, ILayoutHandler> sDefaultLayout = new HashMap<Class<?>, ILayoutHandler>();

	static {
		sDefaultFactory.put(JLabel.class, new JLabelFactory());
		sDefaultFactory.put(JButton.class, new JButtonFactory());
		sDefaultFactory.put(JPanel.class, new JPanelFactory());
		sDefaultFactory.put(JTextArea.class, new JTextAreaFactory());

		sDefaultFactory.put(JMenuBar.class, new JMenuBarFactory());
		sDefaultFactory.put(JMenu.class, new JMenuFactory());
		sDefaultFactory.put(JCheckBox.class, new JCheckBoxFactory());
		sDefaultFactory.put(JRadioButton.class, new JRadioButtonFactory());
		sDefaultFactory.put(JList.class, new JListFactory());

		sDefaultFactory.put(JComboBox.class, new JComboBoxFactory());

		sDefaultFactory.put(JTextField.class, new JTextFieldFactory());
		sDefaultFactory.put(JPasswordField.class, new JPasswordFieldFactory());
		sDefaultFactory.put(JFormattedTextField.class,
				new JFormattedTextFieldFactory());

		sDefaultFactory.put(JTabbedPane.class, new JTabbedPaneFactory());

		// AWT
		sDefaultFactory.put(TextComponent.class, new TextComponentFactory());
		sDefaultFactory.put(TextField.class, new TextFieldFactory());
		sDefaultFactory.put(TextArea.class, new TextAreaFactory());

		// Window
		sDefaultFactory.put(Window.class, new WindowFactory());
		sDefaultFactory.put(Frame.class, new FrameFactory());
		sDefaultFactory.put(JFrame.class, new JFrameFactory());

		sDefaultFactory.put(Dialog.class, new DialogFactory());
		sDefaultFactory.put(FileDialog.class, new FileDialogFactory());
		sDefaultFactory.put(JDialog.class, new JDialogFactory());
	}

	static {
		sDefaultLayout.put(BorderLayout.class, new BorderLayoutHandler());
		sDefaultLayout.put(NullLayout.class, new NullLayoutHandler());
		sDefaultLayout.put(FlowLayout.class, new FlowLayoutHandler());
		sDefaultLayout.put(GridLayout.class, new GridLayoutHandler());
		sDefaultLayout.put(SpringLayout.class, new SpringLayoutHandler());
		sDefaultLayout.put(VerticalFlowLayout.class,
				new VerticalFlowLayoutHandler());
	}

	private static SwingFactory sInstance = new SwingFactory();

	private final Map<Class<?>, ILayoutHandler> mLayoutHandlerMap = new HashMap<Class<?>, ILayoutHandler>();

	private final Map<Class<?>, IComponentFactory> mComponentFactory = new HashMap<Class<?>, IComponentFactory>();

	public SwingFactory() {
		mComponentFactory.putAll(sDefaultFactory);
		mLayoutHandlerMap.putAll(sDefaultLayout);
	}

	private IComponentFactory findFactory(final Class<?> type) {

		if (type == Object.class || type.isPrimitive() || type.isEnum()) {
			return null;
		}

		final IComponentFactory factory = mComponentFactory.get(type);

		if (factory != null) {
			return factory;
		}

		return findFactory(type.getSuperclass());
	}

	private ILayoutHandler findLayoutHandler(final Class<?> type) {
		if (type == null || type == Object.class || type.isPrimitive()
				|| type.isEnum()) {
			return findLayoutHandler(NullLayout.class);
		}

		final ILayoutHandler handler = mLayoutHandlerMap.get(type);

		if (handler != null) {
			return handler;
		}

		return findLayoutHandler(type.getSuperclass());
	}

	private void create(final ComponentContext componentContext) {

		final IComponentFactory factory = findFactory(componentContext
				.getType());

		try {
			factory.createInstance(componentContext);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		componentContext.preInit();

		Container container = null;

		factory.initialize(componentContext);

		if (componentContext.getChildren().size() == 0) {
			componentContext.init();
			return;
		}

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

		UiLayout layout = null;

		layout = componentContext.getType().getAnnotation(UiLayout.class);

		if (layout == null) {
			componentContext.getAnnotatedElement()
					.getAnnotation(UiLayout.class);
			LOGGER.debug("{}|looking @UiLayout from annotate element {}",
					componentContext.getId(), componentContext
							.getAnnotatedElement());
		} else {
			LOGGER.debug("{}|looking @UiLayout from enclosed type {}",
					componentContext.getId(), componentContext.getType());
		}

		final ILayoutHandler layoutHandler = findLayoutHandler(layout == null ? NullLayout.class
				: layout.value());

		LOGGER.debug("{}|using layoutHandler {}", componentContext.getId(),
				layoutHandler.getClass().getSimpleName());

		for (final ComponentContext childContext : componentContext
				.getChildren()) {

			if (!childContext.isDeclared() || childContext.isComponentOf()) {
				continue;
			}

			childContext.setContainer(container);
			childContext.setParentContext(componentContext);
			create(childContext);

			// Don't add to container if this is a menu
			if (childContext.getAnnotatedElement().getAnnotation(
					UiFrameMenu.class) != null) {
				LOGGER.debug("{}|is a UiFrameMenu, not added as Component",
						childContext.getId());
				continue;
			}

			layoutHandler.layoutComponent(childContext);
		}

		componentContext.preInit();
		componentContext.init();

		layoutHandler.finalLayout(componentContext);
	}

	private void plan(final ComponentContext componentContext) {

		if (componentContext.getType().getPackage().getName()
				.startsWith("java")) {
			return;
		}

		LOGGER.debug("{}|start planning", componentContext.getId());

		final Field[] fields = componentContext.getType().getDeclaredFields();

		for (final Field f : fields) {

			if (Modifier.isStatic(f.getModifiers())) {
				LOGGER.debug("{}|ignore static field: {}", componentContext
						.getId(), f.getName());
				continue;
			}

			if (f.getType() == EventHandler.class) {

				componentContext.addPostInit(new Runnable() {

					@Override
					public void run() {
						try {

							f.setAccessible(true);
							final EventHandler<?> handler = (EventHandler<?>) f
									.get(componentContext.getComponent());

							final MethodListener ml = componentContext
									.getActionListeners().get(f.getName());

							if (ml != null) {
								handler.addListener(ml);
							}

							LOGGER.debug("{}|CustomEventHandler added from {}",
									new Object[] { componentContext.getId(),
											f.getName() });

						} catch (final IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (final IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

			}

			if (Modifier.isFinal(f.getModifiers())) {
				LOGGER.debug("{}|ignore final field: {}", componentContext
						.getId(), f.getName());
				continue;
			}

			if (!Component.class.isAssignableFrom(f.getType())) {
				LOGGER.debug("{}|ignore non-component field: {}",
						componentContext.getId(), f.getName());
				continue;
			}

			final UiType uiType = f.getAnnotation(UiType.class);

			final Class<?> childType = uiType == null ? f.getType() : uiType
					.value();

			final UiName childName = f.getAnnotation(UiName.class);

			final String childId = childName == null ? f.getName() : childName
					.value();

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("{}|found {} ({})", new Object[] {
						componentContext.getId(), childId, childType });
			}

			ComponentContext child = componentContext.findChildContext(childId);

			if (child == null) {
				child = new ComponentContext();
			}

			final ComponentContext childContext = child;
			childContext.setId(childId);
			childContext.setAnnotatedElement(f);
			childContext.setDeclared(true);
			childContext.setType(childType);

			final UiComponentOf c = childContext.getAnnotatedElement()
					.getAnnotation(UiComponentOf.class);

			if (c != null) {

				childContext.setComponentOf(true);

				final ComponentContext owner = componentContext
						.findChildContext(c.value());

				if (owner != null) {
					componentContext.addInit(new Runnable() {
						@Override
						public void run() {
							final ComponentContext ownerChild = owner
									.findChildContext(childContext.getId());
							if (ownerChild != null) {

								final Field f = (Field) childContext
										.getAnnotatedElement();

								f.setAccessible(true);

								try {
									f.set(componentContext.getComponent(),
											ownerChild.getComponent());
								} catch (final IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (final IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					});
				}
			} else {

				componentContext.addInit(new Runnable() {
					@Override
					public void run() {
						f.setAccessible(true);
						try {
							f.set(componentContext.getComponent(), childContext
									.getComponent());
						} catch (final Throwable t) {

						}
					}
				});
			}

			componentContext.addChild(childContext);

			final UiAddComponentTo addTo = childContext.getAnnotatedElement()
					.getAnnotation(UiAddComponentTo.class);

			if (addTo != null) {

				final ComponentContext owner = componentContext
						.findChildContext(addTo.value());

				if (owner != null) {

					owner.addInit(new Runnable() {

						@Override
						public void run() {
							owner.addChild(childContext);
						}
					});
				}
			}
		}

		for (final ComponentContext childContext : componentContext
				.getChildren()) {
			plan(childContext);
		}

		final Method[] methods = componentContext.getType()
				.getDeclaredMethods();

		for (final Method m : methods) {
			if (m.getAnnotation(UiInit.class) != null) {
				componentContext.addInit(new Runnable() {
					@Override
					public void run() {
						try {
							m.setAccessible(true);
							m.invoke(componentContext.getComponent());
						} catch (final Throwable t) {
							LOGGER.error("{}|user init method failed {}",
									componentContext.getId(), t);
						}
					}
				});
				continue;
			}

			if (m.getAnnotation(UiPreInit.class) != null) {

				componentContext.addPreInit(new Runnable() {

					@Override
					public void run() {
						try {
							m.invoke(componentContext.getComponent());
						} catch (final Throwable t) {
							LOGGER.error("{}|user preinit method failed {}",
									componentContext.getId(), t);
						}
					}
				});

				continue;
			}

			final String methodName = m.getName();

			if (methodName.startsWith("on")) {
				final int action = methodName.indexOf('_');
				if (action > 0) {
					final String componentName = methodName.substring(2, 3)
							.toLowerCase()
							+ methodName.substring(3, action);
					final String actionName = methodName.substring(action + 1);

					componentContext.addPreInit(new Runnable() {
						@Override
						public void run() {
							final ComponentContext childContext = componentContext
									.findChildContext(componentName);

							if (childContext == null) {
								LOGGER
										.debug("{}|child context not found {}",
												componentContext.getId(),
												componentName);
								return;
							}

							childContext.addActionListener(actionName,
									new MethodListener(componentContext
											.getComponent(), m));

							LOGGER
									.debug(
											"{}|context={}, action={}, source={}, listener={})",
											new Object[] {
													componentContext.getId(),
													childContext.getId(),
													actionName,
													childContext.getId(), m });
						}
					});

					if (LOGGER.isDebugEnabled()) {
						LOGGER
								.debug(
										"{}|Found autowired listener: {}, registered for {} on {}",
										new Object[] {
												componentContext.getId(),
												methodName, componentName,
												actionName });
					}
				}
			}
		}

		LOGGER.debug("{}|planing completed", componentContext.getId());
	}

	private void postInit(final ComponentContext context) {
		for (final ComponentContext child : context.getChildren()) {
			child.postInit();
		}

		context.postInit();
	}

	public <T extends Component> T createComponent(final Class<T> componentClass) {

		final long startTime = System.currentTimeMillis();

		final ComponentContext mainContext = new ComponentContext();
		final UiName name = componentClass.getAnnotation(UiName.class);

		mainContext.setId(name == null ? componentClass.getSimpleName()
				.substring(0, 1).toLowerCase()
				+ componentClass.getSimpleName().substring(1) : name.value());

		mainContext.setType(componentClass);
		mainContext.setAnnotatedElement(componentClass);

		LOGGER.debug("{}|planning...", mainContext.getId());

		plan(mainContext);

		LOGGER.debug("{}|creating...", mainContext.getId());

		create(mainContext);

		LOGGER.debug("{}|postInit...", mainContext.getId());

		postInit(mainContext);

		LOGGER.debug("{}|completed within {} milliseconds",
				mainContext.getId(), System.currentTimeMillis() - startTime);

		return componentClass.cast(mainContext.getComponent());
	}
}
