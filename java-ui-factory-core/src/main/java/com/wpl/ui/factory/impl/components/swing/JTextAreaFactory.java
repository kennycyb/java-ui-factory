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

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.components.JTextAreaProperties;
import com.wpl.ui.annotations.textarea.UiLineWrap;
import com.wpl.ui.annotations.textarea.UiTabSize;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.impl.UiAnnotationHandler;

/**
 * 
 * @since 1.0
 * @author kenny
 * 
 */
public class JTextAreaFactory extends JTextComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(JTextAreaFactory.class);

	@UiAnnotationHandler(JTextAreaProperties.class)
	protected void handleJTextAreaProperties(final ComponentContext context,
			final JTextArea component, final JTextAreaProperties annotate) {

		component.setText(annotate.text());
		LOGGER.debug("{}|JTextArea.setText({})", context.getId(), annotate
				.text());

		if (annotate.rows() > 0) {
			component.setRows(annotate.rows());
			LOGGER.debug("{}|JTextArea.setRows({})", context.getId(), annotate
					.rows());
		}

		if (annotate.columns() > 0) {
			component.setColumns(annotate.columns());
			LOGGER.debug("{}|JTextArea.setColumns({})", context.getId(),
					annotate.columns());
		}
	}

	@UiAnnotationHandler(UiRows.class)
	protected void handleUiRows(final ComponentContext context,
			final JTextArea component, final UiRows annotate) {
		component.setRows(annotate.value());
		LOGGER.debug("{}|JTextArea.setRows({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiColumns.class)
	protected void handleUiColumns(final ComponentContext context,
			final JTextArea component, final UiColumns annotate) {
		component.setColumns(annotate.value());
		LOGGER.debug("{}|JTextArea.setColumns({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiTabSize.class)
	protected void handleUiTabSize(final ComponentContext context,
			final JTextArea component, final UiTabSize annotate) {
		component.setTabSize(annotate.value());
		LOGGER.debug("{}|JTextArea.setTabSize({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiLineWrap.class)
	protected void handleUiLineWrap(final ComponentContext context,
			final JTextArea component, final UiLineWrap annotate) {
		component.setLineWrap(annotate.value());
		LOGGER.debug("{}|JTextArea.setLineWrap({})", context.getId(), annotate
				.value());
	}

}
