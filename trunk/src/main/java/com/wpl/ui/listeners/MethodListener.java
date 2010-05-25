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
package com.wpl.ui.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodListener<E> {
	private static Logger LOGGER = LoggerFactory
			.getLogger(MethodListener.class);

	private final Method mMethod;
	private final Object mListenObject;

	public MethodListener(Object object, Method method) {
		mListenObject = object;
		mMethod = method;
	}

	public void invoke(E args) {
		try {
			this.mMethod.setAccessible(true);
			this.mMethod.invoke(mListenObject, args);

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LOGGER.debug("invoke: {}.{}", mListenObject.getClass()
					.getSimpleName(), mMethod.getName());
		}
	}
}
