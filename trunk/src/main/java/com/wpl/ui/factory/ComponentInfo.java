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
package com.wpl.ui.factory;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ComponentInfo {

    private final String mId;
    private Component mComponent;
    private Field mField;
    private Container mContainer;

    private final Map<String, Method> mActionListeners = new HashMap<String, Method>();

    public ComponentInfo(String id) {
        this.mId = id;
    }

    public void addActionListener(String actionName, Method method) {
        mActionListeners.put(actionName, method);
    }

    public Map<String, Method> getActionListeners() {
        return mActionListeners;
    }

    public String getId() {
        return mId;
    }

    public Component getComponent() {
        return mComponent;
    }

    public void setComponent(Component component) {
        mComponent = component;
    }

    public Field getField() {
        return mField;
    }

    public void setField(Field field) {
        mField = field;
    }

    public Container getContainer() {
        return mContainer;
    }

    public void setContainer(Container container) {
        mContainer = container;
    }
}
