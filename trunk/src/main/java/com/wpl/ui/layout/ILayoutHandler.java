package com.wpl.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;

public interface ILayoutHandler {

    public boolean handleComponent(Container container, Component component,
                                   AnnotatedElement annotate);
}
