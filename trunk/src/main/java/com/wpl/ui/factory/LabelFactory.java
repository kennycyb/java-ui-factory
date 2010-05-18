package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

import javax.swing.JLabel;

import com.wpl.ui.annotations.UiText;

public class LabelFactory extends AbstractUiFactory {

    @Override
    public Component createComponent(Annotation[] annotations) {
        return init(new JLabel(), annotations);
    }

    @Override
    protected void init(Component component, Annotation annotate) {

        if (annotate.annotationType() == UiText.class) {
            ((JLabel)component).setText(((UiText)annotate).value());
            return;
        }

    }
}
