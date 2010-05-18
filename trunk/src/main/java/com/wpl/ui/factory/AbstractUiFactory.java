package com.wpl.ui.factory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.annotation.Annotation;

import javax.swing.JComponent;

import com.wpl.ui.NullLayoutManager;
import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiLocation;
import com.wpl.ui.annotations.UiSize;

public abstract class AbstractUiFactory implements IUiFactory {

    protected Component createComponent(Class<? extends Component> clazz) {
        try {
            return clazz.newInstance();
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Component createComponent(Class<? extends Component> clazz, Annotation[] annotations) {
        return init(createComponent(clazz), annotations);
    }

    protected Component init(Component component, Annotation[] annotations) {

        for (Annotation annotate : annotations) {
            if (annotate.annotationType() == UiSize.class) {
                UiSize size = (UiSize)annotate;
                component.setSize(size.width(), size.height());
                component.setPreferredSize(new Dimension(size.width(), size.height()));
                continue;
            }

            if (annotate.annotationType() == UiLocation.class) {
                UiLocation location = (UiLocation)annotate;
                component.setLocation(location.x(), location.y());
                continue;
            }

            if (annotate.annotationType() == UiFont.class) {
                UiFont font = (UiFont)annotate;
                component.setFont(new Font(font.name(), font.style(), font.size()));
                continue;
            }

            if (component instanceof JComponent) {

                JComponent jcomp = (JComponent)component;

                if (annotate.annotationType() == UiLayout.class) {
                    UiLayout layout = (UiLayout)annotate;

                    if (layout.value() == NullLayoutManager.class) {
                        jcomp.setLayout(null);
                    }
                    else {
                        try {
                            jcomp.setLayout(layout.value().newInstance());
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
                    continue;
                }
            }

            init(component, annotate);
        }

        return component;
    }

    abstract protected void init(Component component, Annotation annotate);
}
