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
package com.github.kennycyb.uifactory.core.factory;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Checkbox;
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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RootPaneContainer;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.components.IComponent;
import com.github.kennycyb.uifactory.core.components.ILabel;
import com.github.kennycyb.uifactory.core.events.EventHandler;
import com.github.kennycyb.uifactory.core.events.IEventHandler;
import com.github.kennycyb.uifactory.core.factory.annotations.DefaultFactory;
import com.github.kennycyb.uifactory.core.factory.annotations.UiAddComponentTo;
import com.github.kennycyb.uifactory.core.factory.annotations.UiComponentOf;
import com.github.kennycyb.uifactory.core.factory.annotations.UiInit;
import com.github.kennycyb.uifactory.core.factory.annotations.UiLayout;
import com.github.kennycyb.uifactory.core.factory.annotations.UiName;
import com.github.kennycyb.uifactory.core.factory.annotations.UiPreInit;
import com.github.kennycyb.uifactory.core.factory.annotations.UiType;
import com.github.kennycyb.uifactory.core.factory.annotations.frame.UiFrameMenu;
import com.github.kennycyb.uifactory.core.factory.annotations.layouts.SplitPaneContent;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.CanvasFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.CheckboxFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.ComponentFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.ContainerFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.DialogFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.FileDialogFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.FrameFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.TextAreaFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.TextComponentFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.TextFieldFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.awt.WindowFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.juf.ULabelFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JButtonFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JCheckBoxFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JComboBoxFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JComponentFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JDialogFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JFormattedTextFieldFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JFrameFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JLabelFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JListFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JMenuBarFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JMenuFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JPanelFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JPasswordFieldFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JProgressBarFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JRadioButtonFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JSliderFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JSpinnerFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JTabbedPaneFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JTextAreaFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JTextFieldFactory;
import com.github.kennycyb.uifactory.core.factory.impl.components.swing.JToolBarFactory;
import com.github.kennycyb.uifactory.core.layout.handlers.BorderLayoutHandler;
import com.github.kennycyb.uifactory.core.layout.handlers.FlowLayoutHandler;
import com.github.kennycyb.uifactory.core.layout.handlers.GridLayoutHandler;
import com.github.kennycyb.uifactory.core.layout.handlers.ILayoutHandler;
import com.github.kennycyb.uifactory.core.layout.handlers.NullLayoutHandler;
import com.github.kennycyb.uifactory.core.layout.handlers.SpringLayoutHandler;
import com.github.kennycyb.uifactory.core.layout.handlers.VerticalFlowLayoutHandler;
import com.github.kennycyb.uifactory.core.layout.managers.NullLayout;
import com.github.kennycyb.uifactory.core.layout.managers.VerticalFlowLayout;
import com.github.kennycyb.uifactory.core.listeners.MethodListener;

/**
 *
 * @since 1.0
 */
public class SwingFactory implements IUiFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SwingFactory.class);

	public static <T extends Component> T create(final Class<T> componentClass) {
		return sInstance.createComponent(componentClass);
	}

	/**
	 * Default component factory that built-in the library.
	 */
	private static Map<Class<?>, IComponentFactory> sDefaultFactory = new HashMap<Class<?>, IComponentFactory>();

	/**
	 * Default layout handler that built-in the library.
	 */
	private static Map<Class<?>, ILayoutHandler> sDefaultLayout = new HashMap<Class<?>, ILayoutHandler>();

	static {

		sDefaultFactory.put(Component.class, new ComponentFactory());
		sDefaultFactory.put(Canvas.class, new CanvasFactory());
		sDefaultFactory.put(Container.class, new ContainerFactory());
		sDefaultFactory.put(Checkbox.class, new CheckboxFactory());

		sDefaultFactory.put(JComponent.class, new JComponentFactory());
		sDefaultFactory.put(JSlider.class, new JSliderFactory());
		sDefaultFactory.put(JSpinner.class, new JSpinnerFactory());
		sDefaultFactory.put(JProgressBar.class, new JProgressBarFactory());

		sDefaultFactory.put(JLabel.class, new JLabelFactory());
		sDefaultFactory.put(JButton.class, new JButtonFactory());
		sDefaultFactory.put(JPanel.class, new JPanelFactory());
		sDefaultFactory.put(JTextArea.class, new JTextAreaFactory());

		sDefaultFactory.put(JMenuBar.class, new JMenuBarFactory());
		sDefaultFactory.put(JMenu.class, new JMenuFactory());
		sDefaultFactory.put(JCheckBox.class, new JCheckBoxFactory());
		sDefaultFactory.put(JRadioButton.class, new JRadioButtonFactory());
		sDefaultFactory.put(JList.class, new JListFactory());
		sDefaultFactory.put(JToolBar.class, new JToolBarFactory());

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

		// JUF
		sDefaultFactory.put(ILabel.class, new ULabelFactory());

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

	protected SwingFactory() {
		mComponentFactory.putAll(sDefaultFactory);
		mLayoutHandlerMap.putAll(sDefaultLayout);
	}

	/**
	 * Find a factory for a specified class.
	 */
	public IComponentFactory findFactory(final Class<?> type) {

		if (type == Object.class || type.isPrimitive() || type.isEnum()) {
			return null;
		}

		IComponentFactory factory = mComponentFactory.get(type);

		if (factory != null) {
			return factory;
		}

		final DefaultFactory defaultFactory = type
				.getAnnotation(DefaultFactory.class);

		if (defaultFactory != null) {
			try {
				factory = defaultFactory.value().newInstance();

				mComponentFactory.put(type, factory);

				return factory;

			} catch (final Exception ignored) {
				LOGGER.warn("Failed to create DefaultFactory {} for {}",
						defaultFactory.value().getName(), type.getName());
			}
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

		if (factory == null) {
			throw new RuntimeException("Unknown Factory for "
					+ componentContext.getType().getName());
		}

		try {
			factory.createInstance(this, componentContext);
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
					componentContext.getId(),
					componentContext.getAnnotatedElement());
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

			if (childContext.getAnnotatedElement().isAnnotationPresent(
					SplitPaneContent.class)) {
				LOGGER.debug(
						"{}|is a SplitPaneContent, not added as Component",
						childContext.getId());
				continue;
			}

			layoutHandler.layoutComponent(childContext);
		}

		componentContext.preInit();
		componentContext.init();

		layoutHandler.finalLayout(componentContext);
	}

	private boolean handleSplitPaneContext(
			final ComponentContext componentContext,
			final ComponentContext childContext) {

		final SplitPaneContent spc = childContext.getAnnotatedElement()
				.getAnnotation(SplitPaneContent.class);

		if (spc == null) {
			return false;
		}

		final ComponentContext splitPaneContext = componentContext
				.findChildContext(spc.splitPaneName());

		if (splitPaneContext == null) {
			LOGGER.warn("{}|is SplitPaneContent, but owner not found {}",
					childContext.getId(), spc.splitPaneName());
			return true;
		}

		childContext.addPostInit(new Runnable() {
			@Override
			public void run() {
				final Component component = splitPaneContext.getComponent();
				final JSplitPane sp = (JSplitPane) component;

				switch (spc.position()) {
				case LEFT:
					sp.setLeftComponent(childContext.getEnclosedComponent());
					LOGGER.debug("{}|set {} to left", splitPaneContext.getId(),
							childContext.getId());
					break;

				case RIGHT:
					sp.setRightComponent(childContext.getEnclosedComponent());
					LOGGER.debug("{}|set {} to right",
							splitPaneContext.getId(), childContext.getId());
					break;

				case TOP:
					sp.setTopComponent(childContext.getComponent());
					LOGGER.debug("{}|set {} to top", splitPaneContext.getId(),
							childContext.getId());
					break;

				case BOTTOM:
					sp.setBottomComponent(childContext.getComponent());
					LOGGER.debug("{}|set {} to bottom",
							splitPaneContext.getId(), childContext.getId());
					break;
				}

			}
		});

		return true;
	}

	private boolean handleUiComponentOf(
			final ComponentContext componentContext,
			final ComponentContext childContext) {

		final UiComponentOf c = childContext.getAnnotatedElement()
				.getAnnotation(UiComponentOf.class);

		if (c == null) {
			return false;
		}

		childContext.setComponentOf(true);

		final ComponentContext owner = componentContext.findChildContext(c
				.value());

		if (owner == null) {
			LOGGER.warn("{}|is UiComponentOf, but owner not found {}",
					childContext.getId(), c.value());
			return true;
		}

		componentContext.addInit(new Runnable() {
			@Override
			public void run() {
				final ComponentContext ownerChild = owner
						.findChildContext(childContext.getId());
				if (ownerChild != null) {

					final Field field = (Field) childContext
							.getAnnotatedElement();

					field.setAccessible(true);

					try {
						field.set(componentContext.getComponent(),
								ownerChild.getComponent());
					} catch (final IllegalArgumentException e) {

						LOGGER.warn("Illegal argument: {}", e);

					} catch (final IllegalAccessException e) {

						LOGGER.warn("Illegal access: {}", e);
					}

				}
			}
		});

		return true;
	}

	/**
	 * Manufacturing Plan
	 *
	 * @param componentContext
	 */
	private void plan(final ComponentContext componentContext) {

		// Do not plan if this is built-in Java's component.

		if (componentContext.getType().getPackage().getName()
				.startsWith("java")) {
			LOGGER.debug("{}|skip planning", componentContext.getId());
			return;
		}

		if (IComponent.class.isAssignableFrom(componentContext.getType())) {
			LOGGER.debug("{}|skip planning", componentContext.getId());
			return;
		}

		LOGGER.debug("{}|start planning", componentContext.getId());

		final Field[] fields = componentContext.getType().getDeclaredFields();

		for (final Field f : fields) {

			if (Modifier.isStatic(f.getModifiers())) {
				LOGGER.debug("{}|ignore static field: {}",
						componentContext.getId(), f.getName());
				continue;
			}

			if (IEventHandler.class.isAssignableFrom(f.getType())) {

				componentContext.addPostInit(new Runnable() {

					@SuppressWarnings("unchecked")
					@Override
					public void run() {
						try {

							f.setAccessible(true);
							final EventHandler<?> handler = (EventHandler<?>) f
									.get(componentContext.getComponent());

							@SuppressWarnings("rawtypes")
							final MethodListener ml = componentContext
							.getActionListeners().get(f.getName());

							if (ml != null) {
								handler.addListener(ml);

								LOGGER.debug(
										"{}|CustomEventHandler added from {}",
										new Object[] {
												componentContext.getId(),
												f.getName() });

							} else {
								LOGGER.warn("Can't find listener: {}",
										f.getName());
							}

						} catch (final IllegalArgumentException e) {
						} catch (final IllegalAccessException e) {
						}
					}
				});

				continue;
			}

			if (Modifier.isFinal(f.getModifiers())) {
				LOGGER.debug("{}|ignore final field: {}",
						componentContext.getId(), f.getName());
				continue;
			}

			if (!(Component.class.isAssignableFrom(f.getType()) || IComponent.class
					.isAssignableFrom(f.getType()))) {
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

			if (!handleUiComponentOf(componentContext, childContext)
					&& !handleSplitPaneContext(componentContext, childContext)) {
				componentContext.addInit(new Runnable() {
					@Override
					public void run() {
						f.setAccessible(true);
						try {
							LOGGER.debug("{}|Setting field: {}",
									componentContext.getId(), f.getName());
							if (IComponent.class.isAssignableFrom(f.getType())) {
								f.set(componentContext.getComponent(),
										childContext.getJxComponent());
							} else {
								f.set(componentContext.getComponent(),
										childContext.getComponent());
							}
							LOGGER.debug("{}|Setting field: {} OK",
									componentContext.getId(), f.getName());
						} catch (final Throwable t) {
							LOGGER.debug(
									"{}|Setting field: {} ERROR - {}",
									new Object[] { componentContext.getId(),
											f.getName(), t.getMessage() });
							LOGGER.error("ERROR {}", t);
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
							.toLowerCase() + methodName.substring(3, action);
					final String actionName = methodName.substring(action + 1);

					if (actionName.equalsIgnoreCase("init")) {
						componentContext.addPreInit(new Runnable() {
							@Override
							public void run() {

								final ComponentContext childContext = componentName
										.equals("this") ? componentContext
												: componentContext
												.findChildContext(componentName);

								if (childContext == null) {
									LOGGER.debug(
											"{}|child context not found {}",
											componentContext.getId(),
											componentName);
									return;
								}

								childContext.addPreInit(new Runnable() {
									@Override
									public void run() {
										try {
											m.setAccessible(true);
											m.invoke(componentContext
													.getComponent());
										} catch (final Throwable t) {
											LOGGER.error(
													"{}|component init method {} failed {}",
													new Object[] {
															componentContext
															.getId(),
															m.getName(), t });
										}
									}
								});
							}
						});
						continue;
					}

					componentContext.addPreInit(new Runnable() {
						@SuppressWarnings("rawtypes")
						@Override
						public void run() {
							final ComponentContext childContext = componentName
									.equals("this") ? componentContext
											: componentContext
											.findChildContext(componentName);

							if (childContext == null) {
								LOGGER.debug("{}|child context not found {}",
										componentContext.getId(), componentName);
								return;
							}

							childContext.addActionListener(
									actionName,
									new MethodListener(componentContext
											.getComponent(), m));

							LOGGER.debug(
									"{}|context={}, action={}, source={}, listener={})",
									new Object[] { componentContext.getId(),
											childContext.getId(), actionName,
											childContext.getId(), m });
						}
					});

					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(
								"{}|Found autowired listener: {}, registered for {} on {}",
								new Object[] { componentContext.getId(),
										methodName, componentName, actionName });
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

	/**
	 * Static instance.
	 *
	 * @return SwingFactory
	 */
	public static SwingFactory instance() {
		return sInstance;
	}
}
