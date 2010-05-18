package com.wpl.ui.annotations;

import java.awt.LayoutManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wpl.ui.NullLayoutManager;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE})
public @interface UiLayout {

    Class<? extends LayoutManager> value() default NullLayoutManager.class;
}
