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
package com.github.kennycyb.uifactory.core.factory.impl.components.swing;

import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.constraints.UiGridLayoutConstraint;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class JPanelFactory extends JComponentFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JPanelFactory.class);

	@UiAnnotationHandler(UiGridLayoutConstraint.class)
	protected void handleUiGridLayoutConstraint(final ComponentContext context,
			final JPanel component, final UiGridLayoutConstraint annotate) {
		final LayoutManager lm = component.getLayout();

		if (lm instanceof GridLayout) {
			final GridLayout glm = (GridLayout) lm;

			glm.setRows(annotate.rows());
			glm.setColumns(annotate.cols());
			glm.setHgap(annotate.horizontalGap());
			glm.setVgap(annotate.verticalGrap());
			component.setLayout(glm);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(JPanel){}.gridLayout={},{},{},{}", new Object[] {
						context.getId(), annotate.rows(), annotate.cols(),
						annotate.horizontalGap(), annotate.verticalGrap() });
			}
			return;
		}
	}
}
