/*
 * Author   : kenny 
 * Date     : May 19, 2010 
 * 
 * Copyright (c) 2010 wongpeiling.com                  
 */

package com.wpl.ui.factory;

import javax.swing.text.JTextComponent;

import com.wpl.ui.annotations.UiText;

public abstract class JTextComponentFactory extends JComponentFactory {

    @UiAnnotationHandler(UiText.class)
    protected void handleUiText(JTextComponent component, UiText annotate) {
        component.setText(annotate.value());
    }
}
