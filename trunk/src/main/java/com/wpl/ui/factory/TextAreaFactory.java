package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

import javax.swing.JTextArea;

import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.UiTabSize;

public class TextAreaFactory extends AbstractUiFactory {

    @Override
    public Component createComponent(Annotation[] annotations) {
        return init(new JTextArea(), annotations);
    }

    @Override
    protected void init(Component component, Annotation annotate) {
        JTextArea textarea = (JTextArea)component;

        if (annotate.annotationType() == UiRows.class) {
            textarea.setRows(((UiRows)annotate).value());
            return;
        }

        if (annotate.annotationType() == UiColumns.class) {
            textarea.setColumns(((UiColumns)annotate).value());
            return;
        }

        if (annotate.annotationType() == UiTabSize.class) {
            textarea.setTabSize(((UiTabSize)annotate).value());
            return;
        }
    }
}
