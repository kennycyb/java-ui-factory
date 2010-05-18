package com.wpl.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;

public class NullLayoutHandler implements ILayoutHandler {

    @Override
    public boolean handleComponent(Container container, Component component,
                                   AnnotatedElement annotate) {
        container.add(component, null);
        return true;
    }
}
