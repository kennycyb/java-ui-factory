package com.wpl.ui.factory;

import java.awt.Component;

import javax.swing.JPanel;

public class JPanelFactory extends JComponentFactory {

    @Override
    protected Component createDefaultComponent() {
        return new JPanel();
    }
}
