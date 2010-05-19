package com.wpl.ui.factory;

import javax.swing.JComponent;

import com.wpl.ui.NullLayout;
import com.wpl.ui.annotations.UiLayout;

public abstract class JComponentFactory extends ComponentFactory {

    @UiAnnotationHandler(UiLayout.class)
    protected void handleUiLayout(JComponent component, UiLayout layout) {
        if (layout.value() == NullLayout.class) {
            component.setLayout(null);
            return;
        }

        try {
            component.setLayout(layout.value().newInstance());
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
