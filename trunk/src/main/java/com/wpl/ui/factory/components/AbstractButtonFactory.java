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

import javax.swing.AbstractButton;

import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.actions.UiActionCommand;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

public abstract class AbstractButtonFactory extends JComponentFactory {

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(ComponentContext context,
			AbstractButton component, UiText text) {
		component.setText(text.value());
	}

	@UiAnnotationHandler(UiActionCommand.class)
	protected void handlerUiActionCommand(ComponentContext context,
			AbstractButton component, UiActionCommand annotate) {
		component.setActionCommand(annotate.value());
	}

}
