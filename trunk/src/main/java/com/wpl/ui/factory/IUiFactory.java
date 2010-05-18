package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

public interface IUiFactory {

    public Component createComponent(Annotation[] annotations);

    public Component createComponent(Class<? extends Component> clazz, Annotation[] annotations);

}
