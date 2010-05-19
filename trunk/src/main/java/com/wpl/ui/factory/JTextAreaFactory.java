package com.wpl.ui.factory;

import java.awt.Component;

import javax.swing.JTextArea;

import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.UiTabSize;

public class JTextAreaFactory extends JTextComponentFactory {

    @UiAnnotationHandler(UiRows.class)
    protected void handleUiText(JTextArea component, UiRows annotate) {
        component.setRows(annotate.value());
    }

    @UiAnnotationHandler(UiColumns.class)
    protected void handleUiText(JTextArea component, UiColumns annotate) {
        component.setColumns(annotate.value());
    }

    @UiAnnotationHandler(UiTabSize.class)
    protected void handleUiText(JTextArea component, UiTabSize annotate) {
        component.setTabSize(annotate.value());
    }

    @Override
    protected Component createDefaultComponent() {
        return new JTextArea();
    }
}
