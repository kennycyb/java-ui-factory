package com.wpl.ui.factory;

import java.awt.Component;

import javax.swing.JFrame;

import com.wpl.ui.NullLayout;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;

public class JFrameFactory extends ComponentFactory {

    @Override
    protected Component createDefaultComponent() {
        return new JFrame();
    }

    @UiAnnotationHandler(UiFrameResizable.class)
    protected void handleUiResizable(JFrame frame, UiFrameResizable resizable) {
        frame.setResizable(resizable.value());
    }

    @UiAnnotationHandler(UiText.class)
    protected void handleUiText(JFrame frame, UiText text) {
        frame.setTitle(text.value());
    }

    @UiAnnotationHandler(UiLayout.class)
    protected void handleUiLayout(JFrame component, UiLayout layout) {
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

    @UiAnnotationHandler(UiFrameCloseOperation.class)
    protected void handleUiFrameCloseOperation(JFrame frame, UiFrameCloseOperation fco) {
        switch (fco.value()) {

            case EXIT:
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                break;

            case DISPOSE:
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                break;

            case HIDE:
                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                break;

            default:
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                break;

        }
    }

}
