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
package com.github.kennycyb.uifactory.core.factory;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.components.IComponent;
import com.github.kennycyb.uifactory.core.events.EventHandler;
import com.github.kennycyb.uifactory.core.listeners.MethodListener;

/**
 *
 * @author Kenny
 */
public class ComponentContext {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponentContext.class);

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
	private IComponent mJxComponent;

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

	public void addCustomEventHandler(final EventHandler<?> customEventHandler) {
		mCustomEventHandler.add(customEventHandler);
	}

	public boolean isComponentOf() {
		return mComponentOf;
	}

	public void setComponentOf(final boolean componentOf) {
		mComponentOf = componentOf;
	}

	public boolean isPack() {
		return mPack;
	}

	public void setPack(final boolean pack) {
		mPack = pack;
	}

	public boolean isAutoWired() {
		return mAutoWired;
	}

	public void setAutoWired(final boolean autoWired) {
		mAutoWired = autoWired;
	}

	public void addPreInit(final Runnable runnable) {
		mPreInit.add(runnable);
	}

	public void preInit() {

		LOGGER.debug("{}|preInit (count={})", getId(), mPreInit.size());

		for (final Runnable r : mPreInit) {
			r.run();
		}

		mPreInit.clear();
	}

	public void addInit(final Runnable runnable) {
		mInit.add(runnable);
	}

	public void init() {

		LOGGER.debug("{}|init (count={})", getId(), mInit.size());

		for (final Runnable r : mInit) {
			r.run();
		}

		mInit.clear();
	}

	public ComponentContext getParentContext() {
		return mParentContext;
	}

	public void setParentContext(final ComponentContext parentContext) {
		mParentContext = parentContext;
	}

	/**
	 * Add an action to be execute later when all component has been created.
	 *
	 * @param runnable
	 */
	public void addPostInit(final Runnable runnable) {
		mPostInit.add(runnable);
	}

	public void postInit() {
		for (final Runnable r : mPostInit) {
			r.run();
		}

		mPostInit.clear();
	}

	public String getParentId() {
		return mParentId;
	}

	public void setParentId(final String parentId) {
		mParentId = parentId;
	}

	public boolean isDeclared() {
		return mDeclared;
	}

	public void setDeclared(final boolean declared) {
		mDeclared = declared;
	}

	public void setId(final String id) {
		mId = id;
	}

	public void addChild(final ComponentContext context) {
		mChildren.add(context);
	}

	public List<ComponentContext> getChildren() {
		return mChildren;
	}

	public ComponentContext findChildContext(final String id) {

		if (getId().equals(id)) {
			return this;
		}

		for (final ComponentContext child : mChildren) {
			if (child.getId().equals(id)) {
				return child;
			}

			final ComponentContext grandChild = child.findChildContext(id);
			if (grandChild != null) {
				return grandChild;
			}
		}

		return null;
	}

	public Component getEnclosedComponent() {

		if (mEnclosedComponent != null) {
			return mEnclosedComponent;
		}

		return getComponent();
	}

	public void setEnclosedComponent(final Component enclosedComponent) {
		mEnclosedComponent = enclosedComponent;
	}

	public Class<?> getType() {
		return mType;
	}

	public void setType(final Class<?> type) {
		mType = type;
	}

	public AnnotatedElement getAnnotatedElement() {
		return mAnnotatedElement;
	}

	public void setAnnotatedElement(final AnnotatedElement annotatedElement) {
		mAnnotatedElement = annotatedElement;
	}

	public void addActionListener(final String actionName, final MethodListener<?> method) {
		mActionListeners.put(actionName, method);
	}

	public Map<String, MethodListener<?>> getActionListeners() {
		return mActionListeners;
	}

	public String getId() {
		return mId;
	}

	/**
	 *
	 * @return Get the component.
	 */
	public Component getComponent() {
		return mJxComponent.getComponent();
	}

	public void setComponent(final Component component) {

		setJxComponent(new IComponent() {

			@Override
			public Component getComponent() {
				return component;
			}
		});
	}

	public IComponent getJxComponent() {
		return mJxComponent;
	}

	public void setJxComponent(final IComponent jxComponent) {
		mJxComponent = jxComponent;
	}

	public Container getContainer() {
		return mContainer;
	}

	public void setContainer(final Container container) {
		mContainer = container;
	}
}
