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
package com.github.kennycyb.uifactory.core.factory.impl.components.juf;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.components.IComponent;
import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.IComponentFactory;
import com.github.kennycyb.uifactory.core.factory.IUiFactory;

/**
 * 
 * @since 1.0
 */
public class UComponentFactory implements IComponentFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UComponentFactory.class);

	@Override
	public void initialize(final ComponentContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createInstance(final IUiFactory uiFactory,
			final ComponentContext context) throws Exception {

		Object instance = null;

		if (context.getParentContext() != null
				&& context.getType().getDeclaringClass() != null
				&& !Modifier.isStatic(context.getType().getModifiers())) {

			final Class<?> innerClass = context.getType();
			final Class<?> outerClass = context.getParentContext().getType();

			final Constructor<?> innerClassConstructor = innerClass
					.getDeclaredConstructor(outerClass);

			innerClassConstructor.setAccessible(true);

			instance = innerClassConstructor.newInstance(context
					.getParentContext().getComponent());
		} else {

			LOGGER.debug("{}|creating from {}", context.getId(),
					context.getType());

			instance = context.getType().newInstance();
		}

		context.setJxComponent(IComponent.class.cast(instance));
	}

}
