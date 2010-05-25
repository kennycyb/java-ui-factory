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

import javax.swing.JPasswordField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiEchoChar;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.UiAnnotationHandler;

public class JPasswordFieldFactory extends JTextFieldFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JPasswordFieldFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JPasswordField.class;
	}

	@UiAnnotationHandler(UiEchoChar.class)
	protected void handleUiEchoChar(FactoryContext factory,
			ComponentContext componentContext, JPasswordField component,
			UiEchoChar annotate) {
		component.setEchoChar(annotate.value());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("(JPasswordField){}.setEchoChar('{}')", new Object[] {
					componentContext.getId(), annotate.value() });
		}
	}
}