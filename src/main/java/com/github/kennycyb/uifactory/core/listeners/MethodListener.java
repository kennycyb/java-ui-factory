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
package com.github.kennycyb.uifactory.core.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.events.IEventListener;

/**
 * Call back to a method.
 * 
 * @since 1.0
 * 
 * @param <E>
 */
public class MethodListener<E> implements IEventListener<E> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MethodListener.class);

	private final Method mMethod;
	private final Object mListenObject;

	/**
	 * 
	 * @since 1.0
	 * 
	 * @param object
	 * @param method
	 */
	public MethodListener(final Object object, final Method method) {
		mListenObject = object;
		mMethod = method;
	}

	/**
	 * @since 1.0
	 * 
	 * @return
	 */
	public String getListenerName() {
		return mListenObject.getClass().getSimpleName();
	}

	/**
	 * @since 1.0
	 * 
	 * @return
	 */
	public String getMethodName() {
		return mMethod.getName();
	}

	/**
	 * @since 1.0
	 */
	public void invoke(final E args) {

		LOGGER.debug("invoke: {}.{}", mListenObject.getClass().getSimpleName(),
				mMethod.getName());

		try {
			this.mMethod.setAccessible(true);
			this.mMethod.invoke(mListenObject, args);

		} catch (final IllegalArgumentException e) {
			LOGGER.debug("IllegalArgument", e);
		} catch (final IllegalAccessException e) {
			LOGGER.debug("IllegalAccess", e);
		} catch (final InvocationTargetException e) {
			LOGGER.debug("InvocationTarget", e);
		}
	}
}
