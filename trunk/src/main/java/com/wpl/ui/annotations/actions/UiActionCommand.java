/*
 * Author   : kenny 
 * Date     : May 19, 2010 
 * 
 * Project  : bp002-common-ui 
 * Package  : com.sicpa.markii.common.ui.annotations 
 * File     : UiActionCommand.java 
 * 
 * Revision : $Id$ 
 * 
 * Copyright (c) 2004 SICPA Product Security SA, all rights reserved.                   
 */

package com.wpl.ui.annotations.actions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UiActionCommand {

    String value();
}
