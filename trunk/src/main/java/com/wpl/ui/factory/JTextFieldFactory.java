/*
 * Author   : kenny 
 * Date     : May 19, 2010 
 * 
 * Copyright (c) 2010 wongpeiling.com                  
 */


package com.wpl.ui.factory;

import java.awt.Component;

import javax.swing.JTextField;

public class JTextFieldFactory extends JTextComponentFactory {

    @Override
    protected Component createDefaultComponent() {
        return new JTextField();
    }
}
