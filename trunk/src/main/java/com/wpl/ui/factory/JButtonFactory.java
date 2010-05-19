package com.wpl.ui.factory;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JButton;

import com.wpl.ui.annotations.UiText;

public class JButtonFactory extends JComponentFactory {

    @Override
    protected Component createDefaultComponent() {
        return new JButton();
    }

    @UiAnnotationHandler(UiText.class)
    protected void handleUiText(AbstractButton component, UiText text) {
        component.setText(text.value());
    }
}
