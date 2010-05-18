package com.wpl.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;

import com.wpl.ui.annotations.UiFlowLayoutConstraint;

public class FlowLayoutHandler implements ILayoutHandler {

    @Override
    public boolean handleComponent(Container container, Component component,
                                   AnnotatedElement annotate) {

        UiFlowLayoutConstraint constraint = annotate.getAnnotation(UiFlowLayoutConstraint.class);

        if (constraint == null) {
            container.add(component, null);
        }
        else {
            container.add(component, constraint.value());
        }
        return true;
    }
}
