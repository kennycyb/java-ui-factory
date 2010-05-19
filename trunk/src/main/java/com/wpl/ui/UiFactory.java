package com.wpl.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiPreInit;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.factory.ButtonFactory;
import com.wpl.ui.factory.IUiFactory;
import com.wpl.ui.factory.LabelFactory;
import com.wpl.ui.factory.PanelFactory;
import com.wpl.ui.factory.TextAreaFactory;
import com.wpl.ui.factory.TextFieldFactory;
import com.wpl.ui.layout.BorderLayoutHandler;
import com.wpl.ui.layout.FlowLayoutHandler;
import com.wpl.ui.layout.ILayoutHandler;
import com.wpl.ui.layout.NullLayoutHandler;
/**
 * A Factory that build the UI, set it's properties using annotations.
 */
public final class UiFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(UiFactory.class);

    private final Map<Class<?>, IUiFactory> mUiFactoryMap = new HashMap<Class<?>, IUiFactory>();
    private final Map<Class<?>, ILayoutHandler> mLayoutHandlerMap = new HashMap<Class<?>, ILayoutHandler>();

    public UiFactory() {

        // Initialize default UI Factory.
        setUiFactory(JLabel.class, new LabelFactory());
        setUiFactory(JButton.class, new ButtonFactory());
        setUiFactory(JPanel.class, new PanelFactory());
        setUiFactory(JTextField.class, new TextFieldFactory());
        setUiFactory(JTextArea.class, new TextAreaFactory());

        // Initialize default Layout handler.
        setLayoutHandler(BorderLayout.class, new BorderLayoutHandler());
        setLayoutHandler(NullLayout.class, new NullLayoutHandler());
        setLayoutHandler(FlowLayout.class, new FlowLayoutHandler());
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
    public void setUiFactory(Class<?> clazz, IUiFactory uiFactory) {

        if (uiFactory == null) {

            if (clazz == JPanel.class) {
                mUiFactoryMap.put(clazz, new PanelFactory());
                return;
            }

            if (clazz == JButton.class) {
                mUiFactoryMap.put(clazz, new ButtonFactory());
                return;
            }

            if (clazz == JLabel.class) {
                setUiFactory(JLabel.class, new LabelFactory());
                return;
            }

            if (clazz == JTextField.class) {
                setUiFactory(JTextField.class, new TextFieldFactory());
                return;
            }
        }

        mUiFactoryMap.put(clazz, uiFactory);
    }

    private class MethodInfo {

        public Method onPreInit;
        public Method onInit;

    }

    private IUiFactory findFactory(Class<?> type) {

        if (type == Object.class || type.isPrimitive() || type.isEnum()) {
            return null;
        }

        IUiFactory factory = this.mUiFactoryMap.get(type);

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

    private MethodInfo findMethodInfo(Method[] methods) {
        MethodInfo info = new MethodInfo();
        for (Method m : methods) {
            if (m.getAnnotation(UiInit.class) != null) {
                info.onInit = m;
                continue;
            }

            if (m.getAnnotation(UiPreInit.class) != null) {
                info.onPreInit = m;
                continue;
            }
        }

        return info;
    }

    public <T extends JPanel> T createPanel(Class<T> panelClazz) {

        try {

            MethodInfo methodInfo = findMethodInfo(panelClazz.getDeclaredMethods());

            // It's impossible that JPanel.class factory is not found, so no null checking is
            // required.
            IUiFactory panelFactory = findFactory(panelClazz);

            T panel = panelClazz.cast(panelFactory.createComponent(panelClazz, panelClazz
                    .getAnnotations()));

            if (methodInfo.onPreInit != null) {
                methodInfo.onPreInit.setAccessible(true);
                methodInfo.onPreInit.invoke(panel);
            }

            UiLayout layout = panelClazz.getAnnotation(UiLayout.class);

            ILayoutHandler layoutHandler;
            if (layout == null) {
                layoutHandler = findLayoutHandler(NullLayout.class);
            }
            else {
                layoutHandler = findLayoutHandler(layout.value());
            }

            LOGGER.debug("layoutHandler={}", layoutHandler.getClass().getSimpleName());

            Field[] fields = panelClazz.getDeclaredFields();

            for (Field f : fields) {

                if (f.get(panel) != null) {
                    // Do not create if it's already created
                }

                IUiFactory factory = this.mUiFactoryMap.get(f.getType());
                if (factory != null) {

                    Component component;

                    UiType type = f.getAnnotation(UiType.class);
                    if (type != null) {
                        component = factory.createComponent(type.value(), f.getAnnotations());
                    }
                    else {
                        component = factory.createComponent(f.getAnnotations());
                    }
                    f.setAccessible(true);
                    f.set(panel, component);

                    if (layoutHandler.handleComponent(panel, component, f)) {
                        LOGGER.debug("Added {} ({}) into {}",
                                     new Object[] {f.getName(),
                                                   component.getClass().getSimpleName(),
                                                   panel.getClass().getSimpleName()});
                    }
                    else {
                        LOGGER.debug("{} ({}) is not added", f.getName(), component.getClass()
                                .getSimpleName());
                    }
                }
            }

            if (methodInfo.onInit != null) {
                methodInfo.onInit.setAccessible(true);
                methodInfo.onInit.invoke(panel);
            }

            return panel;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
