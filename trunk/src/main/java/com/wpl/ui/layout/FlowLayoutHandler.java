/*
 * Copyright 2010 Kenny Chong (wongpeiling.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wpl.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;

import com.wpl.ui.annotations.constraints.UiFlowLayoutConstraint;

public class FlowLayoutHandler implements ILayoutHandler {

    @Override
    public boolean handleComponent(Container container, Component component,
                                   AnnotatedElement annotate) {

        UiFlowLayoutConstraint constraint = annotate.getAnnotation(UiFlowLayoutConstraint.class);

        if (constraint == null) {
            container.add(component, null);
        }
        else {
            container.add(component, constraint.value());
        }
        return true;
    }
}
