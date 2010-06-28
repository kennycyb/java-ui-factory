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

import javax.swing.JButton;

import com.wpl.ui.annotations.button.UiDefaultButton;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.impl.UiAnnotationHandler;

public class JButtonFactory extends AbstractButtonFactory {

	@UiAnnotationHandler(UiDefaultButton.class)
	public void handleUiDefaultButton(final ComponentContext context,
			final JButton component, final UiDefaultButton annotate) {
		component.setDefaultCapable(true);
	}

	@Override
	public void initialize(final ComponentContext context) {
		super.initialize(context);

		final JButton component = (JButton) context.getComponent();
		if (component.getText() == null || component.getText().length() == 0) {
			component.setText(context.getId());
		}
	}
}
