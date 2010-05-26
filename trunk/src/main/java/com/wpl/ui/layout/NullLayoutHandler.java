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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class NullLayoutHandler implements ILayoutHandler {

	private static Logger LOGGER = LoggerFactory
			.getLogger(NullLayoutHandler.class);

	@Override
	public void layoutComponent(FactoryContext factoryContext,
			ComponentContext componentContext) {

		// Sanity checking
		if (componentContext == null || componentContext.getContainer() == null
				|| componentContext.getComponent() == null) {
			return;
		}

		componentContext.getContainer().add(componentContext.getComponent(),
				null);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{} added to {}", componentContext.getId(),
					componentContext.getContainer().getClass());
		}
	}

	@Override
	public void finalLayout(FactoryContext factoryContext,
			ComponentContext containerContext) {
		// TODO Auto-generated method stub

	}
}
