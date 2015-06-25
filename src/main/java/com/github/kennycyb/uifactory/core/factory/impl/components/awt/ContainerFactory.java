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
package com.github.kennycyb.uifactory.core.factory.impl.components.awt;

import java.awt.Container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiLayout;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;

public class ContainerFactory extends ComponentFactory {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContainerFactory.class);

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(final ComponentContext context,
			final Container component, final UiLayout layout) {

		try {
			component.setLayout(layout.value().newInstance());
		} catch (final InstantiationException e) {
			LOGGER.error("{}|Failed to create Layout Manager - {}, error={} ",
					new Object[] { context.getId(), layout.value(), e });
		} catch (final IllegalAccessException e) {
			LOGGER.error("{}|Failed to create LayoutManager - {}, error={}",
					new Object[] { context.getId(), layout.value(), e });
		}
	}
}
