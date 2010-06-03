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
package com.wpl.ui.factory.components.awt;

import java.awt.Checkbox;
import java.awt.event.ItemListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.listeners.MethodListenerProxy;

/**
 * 
 * @since 1.0
 */
public class CheckboxFactory extends ComponentFactory {
	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory
			.getLogger(CheckboxFactory.class);

	@Override
	protected void wireComponent(ComponentContext context) {

		super.wireComponent(context);

		Checkbox component = (Checkbox) context.getComponent();

		MethodListenerProxy<ItemListener> itemListener = new MethodListenerProxy<ItemListener>(
				ItemListener.class, context.getActionListeners());

		if (itemListener.hasListeningMethod()) {
			component.addItemListener(itemListener.getProxy());
		}
	}
}
