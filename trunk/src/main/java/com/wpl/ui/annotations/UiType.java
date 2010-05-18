package com.wpl.ui.annotations;

import java.awt.Component;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UiType {

    Class<? extends Component> value();
}
