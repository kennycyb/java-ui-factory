package com.wpl.ui.factory;

import java.awt.Component;
import java.lang.annotation.Annotation;

public interface IUiFactory {

    /**
     * Create a component with default type.
     * 
     * @param annotations
     * @return
     */
    public Component createComponent(Annotation[] annotations);

    /**
     * Create a custom component with given type.
     * 
     * @param clazz
     * @param annotations
     * @return
     */
    @Deprecated
    public Component createComponent(Class<? extends Component> clazz, Annotation[] annotations);

    /**
     * Create a custom component with given type, where this custom class is an inner class.
     * 
     * @param clazz
     * @param annotations
     * @param outer
     * @return
     */
    public Component createComponent(Class<? extends Component> clazz, Annotation[] annotations,
                                     Object outer);
}