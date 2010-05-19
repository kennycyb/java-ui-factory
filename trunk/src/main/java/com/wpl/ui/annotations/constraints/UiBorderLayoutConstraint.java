package com.wpl.ui.annotations.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BorderLayout constraint.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD })
public @interface UiBorderLayoutConstraint {

	/**
	 * One of BorderLayout.CENTER, BorderLayout.NORTH, BorderLayout.EAST,
	 * BorderLayout.SOUTH, BorderLayout.WEST
	 * 
	 * @return
	 */
	String value();
}
