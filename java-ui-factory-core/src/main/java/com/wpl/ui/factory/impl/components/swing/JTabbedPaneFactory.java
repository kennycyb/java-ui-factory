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
package com.wpl.ui.factory.impl.components.swing;

import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.ComponentContext;

/**
 * 
 * @since 1.0
 */
public class JTabbedPaneFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JTabbedPaneFactory.class);

	@Override
	public void initialize(final ComponentContext context) {
		super.initialize(context);

		context.addPostInit(new Runnable() {

			@Override
			public void run() {

				JTabbedPane tabPane = (JTabbedPane) context.getComponent();

				for (ComponentContext child : context.getChildren()) {
					if (child.getComponent() == null) {
						continue;
					}
					tabPane.add(child.getComponent());
				}
			}
		});
	}
}
