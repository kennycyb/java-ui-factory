package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

import javax.swing.JButton;

import com.wpl.ui.annotations.UiText;

public class ButtonFactory extends AbstractUiFactory {

    @Override
    public Component createComponent(Annotation[] annotations) {
        return init(new JButton(), annotations);
    }

    @Override
    protected void init(Component component, Annotation annotate) {

        JButton button = (JButton)component;

        if (annotate.annotationType() == UiText.class) {
            button.setText(((UiText)annotate).value());
            return;
        }

    }
}
