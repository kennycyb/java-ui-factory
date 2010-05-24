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
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentContext {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ComponentContext.class);

	/**
	 * The type of the component.
	 */
	private Class<?> mType;

	/**
	 * The ID of the component. Should be unique within a Form.
	 */
	private String mId;

	/**
	 * The component.
	 */
	private Component mComponent;

	/**
	 * The container that contain this component.
	 */
	private Container mContainer;
	private AnnotatedElement mAnnotatedElement;
	private Component mEnclosedComponent;

	private Object mActionListener;
	private final Map<String, Method> mActionListeners = new HashMap<String, Method>();

	private final List<ComponentContext> mChildren = new ArrayList<ComponentContext>();

	private boolean mDeclared = true;

	public ComponentContext() {
	}

	public boolean isDeclared() {
		return mDeclared;
	}

	public void setDeclared(boolean declared) {
		mDeclared = declared;
	}

	public void setId(String id) {
		mId = id;
	}

	public void addChild(ComponentContext context) {
		mChildren.add(context);
		// if (LOGGER.isDebugEnabled()) {
		// LOGGER.debug("{} - child added {} ({})", new Object[] {
		// this.getId(), context.getId(), context.getType() });
		// }
	}

	public List<ComponentContext> getChildren() {
		return mChildren;
	}

	public void setActionListener(Object actionListener) {
		mActionListener = actionListener;
	}

	public Object getActionListener() {
		return mActionListener;
	}

	public Component getEnclosedComponent() {
		return mEnclosedComponent == null ? mComponent : mEnclosedComponent;
	}

	public void setEnclosedComponent(Component enclosedComponent) {
		mEnclosedComponent = enclosedComponent;
	}

	public Class<?> getType() {
		return mType;
	}

	public void setType(Class<?> type) {
		mType = type;
	}

	public AnnotatedElement getAnnotatedElement() {
		return mAnnotatedElement;
	}

	public void setAnnotatedElement(AnnotatedElement annotatedElement) {
		mAnnotatedElement = annotatedElement;
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

	public Container getContainer() {
		return mContainer;
	}

	public void setContainer(Container container) {
		mContainer = container;
	}
}
