package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

import javax.swing.JPanel;

public class PanelFactory extends AbstractUiFactory {

    @Override
    protected void init(Component component, Annotation annotate) {

    }

    @Override
    public Component createComponent(Annotation[] annotations) {
        return init(new JPanel(), annotations);
    }
}
