/*
 * Author   : kenny 
 * Date     : May 17, 2010 
 * 
 * Project  : bp002-common-ui 
 * Package  : com.sicpa.markii.common.ui 
 * File     : NullLayoutManager.java 
 * 
 * Revision : $Id$ 
 * 
 * Copyright (c) 2004 SICPA Product Security SA, all rights reserved.                   
 */

package com.wpl.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class NullLayoutManager implements LayoutManager {

    @Override
    public void addLayoutComponent(String arg0, Component arg1) {
    // TODO Auto-generated method stub

    }

    @Override
    public void layoutContainer(Container arg0) {
    // TODO Auto-generated method stub

    }

    @Override
    public Dimension minimumLayoutSize(Container arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Dimension preferredLayoutSize(Container arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeLayoutComponent(Component arg0) {
    // TODO Auto-generated method stub

    }

}
