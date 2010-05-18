package com.wpl.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * This class is for @UiLayout. When using a NullLayoutManager using @UiLayout, @UiLocation must be
 * used to specify the location of the component, and @UiSize to specify the size of a component.
 */
public class NullLayoutManager implements LayoutManager {

    @Override
    public void addLayoutComponent(String arg0, Component arg1) {}

    @Override
    public void layoutContainer(Container arg0) {}

    @Override
    public Dimension minimumLayoutSize(Container arg0) {
        return null;
    }

    @Override
    public Dimension preferredLayoutSize(Container arg0) {
        return null;
    }

    @Override
    public void removeLayoutComponent(Component arg0) {}
}

