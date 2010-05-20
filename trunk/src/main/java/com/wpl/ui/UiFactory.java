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
import java.awt.Panel;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
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
import com.wpl.ui.factory.ComponentInfo;
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

    private void createFields(FactoryContext factoryInfo, Object object,
                              Class<? extends Component> clazz, Container container) {

        UiLayout layout = clazz.getAnnotation(UiLayout.class);

        ILayoutHandler layoutHandler;
        if (layout == null) {
            layoutHandler = findLayoutHandler(NullLayout.class);
        }
        else {
            layoutHandler = findLayoutHandler(layout.value());
        }

        LOGGER.debug("layoutHandler={}", layoutHandler.getClass().getSimpleName());

        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {

            if (!Component.class.isAssignableFrom(f.getType())) {
                LOGGER.debug("ignore non-component field: {}", f.getName());
                continue;
            }

            LOGGER.debug("building: {} of type {}", f.getName(), f.getType());

            try {

                f.setAccessible(true);

                if (f.get(object) != null) {
                    // Do not create if it's already created
                    continue;
                }

                UiType uiType = f.getAnnotation(UiType.class);

                Class<? extends Component> type = (uiType == null ? (Class<? extends Component>)f
                        .getType() : uiType.value());

                Component component = createComponent(factoryInfo, object, type, f);

                if (component == null) {
                    continue;
                }

                f.set(object, component);

                UiName uiName = f.getAnnotation(UiName.class);

                String id = uiName == null ? f.getName() : uiName.value();

                ComponentInfo cInfo = factoryInfo.mComponents.get(id);
                if (cInfo == null) {
                    cInfo = new ComponentInfo(id);
                    factoryInfo.mComponents.put(id, cInfo);
                }

                cInfo.setComponent(component);
                cInfo.setField(f);
                cInfo.setContainer(container);

                if (layoutHandler.handleComponent(container, component, f)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Added {} ({}) into {}",
                                     new Object[] {f.getName(),
                                                   component.getClass().getSimpleName(),
                                                   object.getClass().getSimpleName()});
                    }
                }
                else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("{} ({}) is not added", f.getName(), component.getClass()
                                .getSimpleName());
                    }
                }
            }
            catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


    public <T extends JFrame> T createFrame(Class<T> frameClazz) {

        LOGGER.debug("creating frame from: {}", frameClazz.getSimpleName());

        IComponentFactory frameFactory = findFactory(frameClazz);

        Class[] innerClasses = frameClazz.getDeclaredClasses();

        T frame = frameClazz.cast(frameFactory.createComponent(frameClazz, frameClazz
                .getAnnotations()));

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

        LOGGER.debug("creating container from: {}", containerClass.getSimpleName());

        // It's impossible that JPanel.class factory is not found, so no null checking is
        // required.
        IComponentFactory panelFactory = findFactory(containerClass);

        T container = containerClass.cast(panelFactory.createComponent(containerClass,
                                                                       containerClass
                                                                               .getAnnotations(),
                                                                       outer));

        if (container == null) {
            LOGGER.warn("Failed to create container from {}", containerClass.getSimpleName());
            return null;
        }

        if (factoryInfo == null) {
            factoryInfo = new FactoryContext(container);
        }

        LOGGER.debug("created {} from type {}", container.getClass().getSimpleName(),
                     containerClass.getSimpleName());

        factoryInfo.onPreInit();

        createFields(factoryInfo, container, containerClass, container);

        factoryInfo.onInit();

        return container;
    }

    private <T extends Component> T createComponent(FactoryContext factoryInfo, Object outer,
                                                    Class<T> componentClass,
                                                    AnnotatedElement annotate) {

        if (componentClass == Component.class) {
            LOGGER.debug("Unable to create component");
            return null;
        }

        LOGGER.debug("creating component from: {}", componentClass.getSimpleName());

        if (JPanel.class.isAssignableFrom(componentClass)
            || Panel.class.isAssignableFrom(componentClass)) {
            return componentClass.cast(createContainer(factoryInfo,
                                                       (Class<? extends Container>)componentClass,
                                                       outer));
        }

        IComponentFactory factory = findFactory(componentClass);

        if (factory == null) {
            LOGGER.info("Unable to find factory for type: {}", componentClass.getSimpleName());
            return null;
        }

        return componentClass.cast(factory.createComponent(componentClass, annotate
                .getAnnotations()));
    }
}
