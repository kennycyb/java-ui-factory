package com.wpl.ui.factory;

import java.awt.Component;

import javax.swing.JLabel;

import com.wpl.ui.annotations.UiText;

public class JLabelFactory extends JComponentFactory {

    @Override
    protected Component createDefaultComponent() {
        return new JLabel();
    }

    @UiAnnotationHandler(UiText.class)
    protected void handleUiText(JLabel component, UiText annotate) {
        component.setText(annotate.value());
    }
}
