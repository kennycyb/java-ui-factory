package com.wpl.ui.annotations;

/**
 * Allow auto wire of event
 * 
 * @author kenny
 */
public @interface UiAutoWired {

    boolean value() default true;
}
