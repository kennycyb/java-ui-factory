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

import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.textarea.UiLineWrap;
import com.wpl.ui.annotations.textarea.UiTabSize;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

/**
 * 
 * @since 1.0
 * @author kenny
 * 
 */
public class JTextAreaFactory extends JTextComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(JTextAreaFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JTextArea.class;
	}

	@UiAnnotationHandler(UiRows.class)
	protected void handleUiText(ComponentContext context, JTextArea component,
			UiRows annotate) {
		component.setRows(annotate.value());
		LOGGER.debug("(JTextArea){}.setRows({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiColumns.class)
	protected void handleUiText(ComponentContext context, JTextArea component,
			UiColumns annotate) {
		component.setColumns(annotate.value());
		LOGGER.debug("(JTextArea){}.setColumns({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiTabSize.class)
	protected void handleUiText(ComponentContext context, JTextArea component,
			UiTabSize annotate) {
		component.setTabSize(annotate.value());
		LOGGER.debug("(JTextArea){}.setTabSize({})", context.getId(), annotate
				.value());
	}

	@UiAnnotationHandler(UiLineWrap.class)
	protected void handlerUiLineWrap(ComponentContext context,
			JTextArea component, UiLineWrap annotate) {
		component.setLineWrap(annotate.value());
		LOGGER.debug("(JTextArea){}.setLineWrap({})", context.getId(), annotate
				.value());
	}

}
