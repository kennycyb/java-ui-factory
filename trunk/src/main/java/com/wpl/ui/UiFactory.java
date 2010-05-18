package com.wpl.ui;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wpl.ui.annotations.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.factory.ButtonFactory;
import com.wpl.ui.factory.IUiFactory;
import com.wpl.ui.factory.LabelFactory;
import com.wpl.ui.factory.PanelFactory;
import com.wpl.ui.factory.TextFieldFactory;

public final class UiFactory {

    private final Map<Class<?>, IUiFactory> mUiFactoryMap = new HashMap<Class<?>, IUiFactory>();

    public static JLabel createLabel() {
        JLabel label = new JLabel();

        return label;
    }

    public UiFactory() {
        setUiFactory(JLabel.class, new LabelFactory());
        setUiFactory(JButton.class, new ButtonFactory());
        setUiFactory(JPanel.class, new PanelFactory());
        setUiFactory(JTextField.class, new TextFieldFactory());
    }

    public void setUiFactory(Class<?> clazz, IUiFactory uiFactory) {
        mUiFactoryMap.put(clazz, uiFactory);
    }

    public <T extends JPanel> T createPanel(Class<T> panelClazz) {
        try {

            IUiFactory panelFactory = mUiFactoryMap.get(panelClazz);
            if (panelFactory == null) {
                panelFactory = mUiFactoryMap.get(JPanel.class);
            }

            T panel = panelClazz.cast(panelFactory.createComponent(panelClazz, panelClazz
                    .getAnnotations()));

            Method[] methods = panelClazz.getDeclaredMethods();

            Method init = null;
            for (Method m : methods) {
                if (m.getAnnotation(UiInit.class) != null) {
                    init = m;
                    break;
                }
            }

            UiLayout layout = panelClazz.getAnnotation(UiLayout.class);
            boolean isNullLayout = layout != null && layout.value() == NullLayoutManager.class;

            Field[] fields = panelClazz.getDeclaredFields();

            for (Field f : fields) {

                IUiFactory factory = this.mUiFactoryMap.get(f.getType());
                if (factory != null) {

                    Component component;

                    UiType type = f.getAnnotation(UiType.class);
                    if (type != null) {
                        component = factory.createComponent(type.value(), f.getAnnotations());
                    }
                    else {
                        component = factory.createComponent(f.getAnnotations());
                    }
                    f.setAccessible(true);
                    f.set(panel, component);

                    if (isNullLayout) {
                        panel.add(component, null);
                        continue;
                    }

                    UiBorderLayoutConstraint borderLayout = f
                            .getAnnotation(UiBorderLayoutConstraint.class);
                    if (borderLayout != null) {
                        panel.add(component, borderLayout.value());
                        continue;
                    }
                }
            }

            if (init != null) {
                init.setAccessible(true);
                init.invoke(panel);
            }

            return panel;
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
