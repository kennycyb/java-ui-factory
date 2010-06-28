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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiTextFormat;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.impl.UiAnnotationHandler;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class JFormattedTextFieldFactory extends JTextFieldFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JFormattedTextFieldFactory.class);

	@UiAnnotationHandler(UiTextFormat.class)
	protected void handleUiTextFormat(ComponentContext context,
			JFormattedTextField component, UiTextFormat annotate) {

		JFormattedTextField.AbstractFormatter formatter = null;

		if (annotate.formatter() == DateFormat.class) {
			formatter = new DateFormatter(new SimpleDateFormat(annotate
					.pattern()));
		}

		if (formatter == null) {
			LOGGER
					.warn(
							"(JFormattedTextField){} - unable to construct formatter ({}, {})",
							new Object[] { context.getId(),
									annotate.formatter().getSimpleName(),
									annotate.pattern() });
			return;
		}

		component.setFormatterFactory(new DefaultFormatterFactory(formatter));
		LOGGER.debug("(JFormattedTextField){}.setFormatterFactory()", context
				.getId());

	}
}
