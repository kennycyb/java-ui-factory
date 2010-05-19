package com.wpl.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;

import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;

public class BorderLayoutHandler implements ILayoutHandler {

    public boolean handleComponent(Container container, Component component,
                                   AnnotatedElement annotate) {
        UiBorderLayoutConstraint constraint = annotate
                .getAnnotation(UiBorderLayoutConstraint.class);

        if (constraint == null) {
            return false;
        }

        container.add(component, constraint.value());
        return true;
    }
}
