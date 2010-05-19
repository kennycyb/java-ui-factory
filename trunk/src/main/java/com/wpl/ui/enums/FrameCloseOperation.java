/*
 * Author   : kenny 
 * Date     : May 19, 2010 
 * 
 * Copyright (c) 2010 wongpeiling.com
 */

package com.wpl.ui.enums;

public enum FrameCloseOperation {

    /**
     * Do nothing when frame is closed.
     */
    NOTHING,

    /**
     * Hide when frame is closed.
     */
    HIDE,

    /**
     * Frame will be disposed when is closed.
     */
    DISPOSE,

    /**
     * Application will exit when a frame is closed.
     */
    EXIT
}
