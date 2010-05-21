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
package com.wpl.ui.factory.components;

import javax.swing.JComponent;

import com.wpl.ui.NullLayout;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

/**
 * @since 1.0
 * @author kenny
 * 
 */
public abstract class JComponentFactory extends ComponentFactory {

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(ComponentContext context,
			JComponent component, UiLayout layout) {
		if (layout.value() == NullLayout.class) {
			component.setLayout(null);
			return;
		}

		try {
			component.setLayout(layout.value().newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
