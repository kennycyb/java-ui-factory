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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.events.EventHandler;
import com.wpl.ui.listeners.MethodListener;

public class ComponentContext {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ComponentContext.class);

	private boolean mAutoWired = true;
	private boolean mPack = true;
	private boolean mComponentOf = false;

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

	private final Map<String, MethodListener<?>> mActionListeners = new HashMap<String, MethodListener<?>>();

	private final List<ComponentContext> mChildren = new ArrayList<ComponentContext>();

	private ComponentContext mParentContext;

	private boolean mDeclared = true;

	private String mParentId;

	private final List<Runnable> mPreInit = new ArrayList<Runnable>();

	private final List<Runnable> mPostInit = new ArrayList<Runnable>();

	private final List<Runnable> mInit = new ArrayList<Runnable>();

	private final List<EventHandler<?>> mCustomEventHandler = new ArrayList<EventHandler<?>>();

	public ComponentContext() {
	}

	public void addCustomEventHandler(EventHandler<?> customEventHandler) {
		mCustomEventHandler.add(customEventHandler);
	}

	public boolean isComponentOf() {
		return mComponentOf;
	}

	public void setComponentOf(boolean componentOf) {
		mComponentOf = componentOf;
	}

	public boolean isPack() {
		return mPack;
	}

	public void setPack(boolean pack) {
		mPack = pack;
	}

	public boolean isAutoWired() {
		return mAutoWired;
	}

	public void setAutoWired(boolean autoWired) {
		mAutoWired = autoWired;
	}

	public void addPreInit(Runnable runnable) {
		mPreInit.add(runnable);
	}

	public void preInit() {

		LOGGER.debug("{}|preInit (count={})", this.getId(), mPreInit.size());

		for (Runnable r : mPreInit) {
			r.run();
		}

		mPreInit.clear();
	}

	public void addInit(Runnable runnable) {
		mInit.add(runnable);
	}

	public void init() {

		LOGGER.debug("{}|init (count={})", this.getId(), mInit.size());

		for (Runnable r : mInit) {
			r.run();
		}

		mInit.clear();
	}

	public ComponentContext getParentContext() {
		return mParentContext;
	}

	public void setParentContext(ComponentContext parentContext) {
		mParentContext = parentContext;
	}

	/**
	 * Add an action to be execute later when all component has been created.
	 * 
	 * @param runnable
	 */
	public void addPostInit(Runnable runnable) {
		mPostInit.add(runnable);
	}

	public void postInit() {
		for (Runnable r : mPostInit) {
			r.run();
		}

		mPostInit.clear();
	}

	public String getParentId() {
		return mParentId;
	}

	public void setParentId(String parentId) {
		mParentId = parentId;
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
	}

	public List<ComponentContext> getChildren() {
		return mChildren;
	}

	public ComponentContext findChildContext(String id) {

		if (this.getId().equals(id)) {
			return this;
		}

		for (ComponentContext child : mChildren) {
			if (child.getId().equals(id)) {
				return child;
			}

			ComponentContext grandChild = child.findChildContext(id);
			if (grandChild != null) {
				return grandChild;
			}
		}

		return null;
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

	public void addActionListener(String actionName, MethodListener<?> method) {
		mActionListeners.put(actionName, method);
	}

	public Map<String, MethodListener<?>> getActionListeners() {
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
