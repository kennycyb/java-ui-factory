/*
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

import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.components.JTextFieldProperties;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

/**
 * Factory that create JTextField
 * 
 * @author kenny
 * @since 1.0
 */
public class JTextFieldFactory extends JTextComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(JTextFieldFactory.class);

	@UiAnnotationHandler(UiColumns.class)
	void handleAnnotation(ComponentContext context, JTextField component,
			UiColumns annotate) {

		component.setColumns(annotate.value() < 0 ? 0 : annotate.value());

		LOGGER.debug("{}|JTextField.setColumns({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(JTextFieldProperties.class)
	void handleAnnotation(ComponentContext context, JTextField component,
			JTextFieldProperties annotate) {

		component.setText(annotate.text());

		LOGGER.debug("{}|JTextField.setText(\"{}\")", context.getId(), annotate
				.text());
	}

}
