package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

import javax.swing.JTextField;

import com.wpl.ui.annotations.UiText;

public class TextFieldFactory extends AbstractUiFactory {

    @Override
    public Component createComponent(Annotation[] annotations) {
        return init(new JTextField(), annotations);
    }

    @Override
    protected void init(Component component, Annotation annotate) {

        JTextField text = (JTextField)component;

        if (annotate.annotationType() == UiText.class) {
            text.setText(((UiText)annotate).value());
            return;
        }
    }
}
