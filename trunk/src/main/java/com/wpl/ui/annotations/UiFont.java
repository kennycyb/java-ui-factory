package com.wpl.ui.annotations;

import java.awt.Font;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD})
public @interface UiFont {

    String name() default "Arial";

    int style() default Font.PLAIN;

    int size() default 12;
}
