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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FactoryContext {

	/**
	 * The object that was being manufactured.
	 */
	private Object mObject;

	/**
	 * Method to invoke before starting any manufacturing process.
	 */
	private Method mPreInitMethod;

	/**
	 * Method to invoke once manufacturing is done by the factory.
	 */
	private Method mInitMethod;

	private Field mFactoryContextHolder;

	private boolean mAutoWired = true;

	public FactoryContext() {
	}

	public Object getObject() {
		return mObject;
	}

	public void setObject(Object object) {
		mObject = object;
	}

	public void setAutoWired(boolean autoWired) {
		mAutoWired = autoWired;
	}

	public void setInitMethod(Method initMethod) {
		mInitMethod = initMethod;
	}

	public void setPreInitMethod(Method preInitMethod) {
		mPreInitMethod = preInitMethod;
	}

	/**
	 * List of component that has been manufactured.
	 */
	private final Map<String, ComponentContext> mComponents = new HashMap<String, ComponentContext>();

	private final Map<String, ComponentContext> mBindableComponents = new HashMap<String, ComponentContext>();

	public ComponentContext findComponentContext(String id) {
		ComponentContext context = mComponents.get(id);
		if (context == null) {
			context = new ComponentContext();
			context.setId(id);
			mComponents.put(id, context);
		}

		return context;
	}

	public void addBindableComponent(ComponentContext context) {
		mBindableComponents.put(context.getId(), context);
	}

	public Collection<ComponentContext> getbindableComponent() {
		return mBindableComponents.values();
	}

	public Collection<ComponentContext> getComponents() {
		return mComponents.values();
	}

	public void onPreInit() {
		invoke(this.mPreInitMethod);
	}

	public void onInit() {
		invoke(this.mInitMethod);
	}

	public void setFactoryContextHolder(Field factoryContextHolder) {
		mFactoryContextHolder = factoryContextHolder;
	}

	public Field getFactoryContextHolder() {
		return mFactoryContextHolder;
	}

	private void invoke(Method method) {
		if (method == null) {
			return;
		}

		method.setAccessible(true);

		try {
			method.invoke(this.mObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * True if auto-wired component -> actions, false otherwise. Default is true
	 * if @UiAutoWired is not declared.
	 * 
	 * @return
	 */

	public boolean isAutoWired() {
		return mAutoWired;
	}
}
