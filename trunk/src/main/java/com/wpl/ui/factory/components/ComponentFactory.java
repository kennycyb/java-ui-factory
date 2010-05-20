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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiLocation;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.factory.IComponentFactory;
import com.wpl.ui.factory.UiAnnotationHandler;

public abstract class ComponentFactory implements IComponentFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(ComponentFactory.class);

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
            UiAnnotationHandler annotated = m.getAnnotation(UiAnnotationHandler.class);
            if (annotated != null) {
                mAnnotationHandlerMap.put(annotated.value(), m);
            }
        }
    }

    @Override
    public Component createComponent(Class<? extends Component> clazz, Annotation[] annotations,
                                     Object outer) {

        if (outer == null) {
            return createComponent(clazz, annotations);
        }

        try {
            Constructor<? extends Component> innerConstructor = clazz.getConstructor(outer
                    .getClass());
            Component innerObject = innerConstructor.newInstance(outer);
            return init(innerObject, annotations);
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    protected Component createComponent(Class<? extends Component> clazz) {
        try {

            Class<?> enclosingClass = clazz.getEnclosingClass();

            if (enclosingClass != null) {
                LOGGER.debug("This is inner class");
                return null;
            }

            return clazz.newInstance();
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    abstract protected Component createDefaultComponent();

    @Override
    public Component createComponent(Class<? extends Component> clazz, Annotation[] annotations) {
        return init(createComponent(clazz), annotations);
    }

    @Override
    public Component createComponent(Annotation[] annotations) {
        return init(createDefaultComponent(), annotations);
    }

    @UiAnnotationHandler(UiSize.class)
    protected void handleUiSize(Component component, UiSize size) {
        component.setSize(size.width(), size.height());
        component.setPreferredSize(new Dimension(size.width(), size.height()));
    }

    @UiAnnotationHandler(UiLocation.class)
    protected void handleUiLocation(Component component, UiLocation location) {
        component.setLocation(location.x(), location.y());
    }

    @UiAnnotationHandler(UiFont.class)
    protected void handleUiFont(Component component, UiFont font) {
        component.setFont(new Font(font.name(), font.style(), font.size()));
    }
    
    @UiAnnotationHandler(UiName.class)
    protected void handleUiName(Component component, UiName name) {
    	component.setName(name.value());
    }

    protected Component init(Component component, Annotation[] annotations) {

        if (component == null) {
            return null;
        }

        // Build the current component.

        for (Annotation annotate : annotations) {

            Method handler = mAnnotationHandlerMap.get(annotate.annotationType());
            if (handler == null) {
                LOGGER.debug("No handler for {}", annotate.annotationType());
                continue;
            }

            try {
                handler.invoke(this, component, annotate);
            }
            catch (IllegalArgumentException e1) {

                LOGGER.warn("IllegalArgument: {}({}, {})", new Object[] {
                                                                         handler.getName(),
                                                                         component.getClass()
                                                                                 .getSimpleName(),
                                                                         annotate.annotationType()
                                                                                 .getSimpleName()});

            }
            catch (IllegalAccessException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            catch (InvocationTargetException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return component;
    }

}
