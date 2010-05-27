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

import java.awt.LayoutManager;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.layout.managers.NullLayout;

public class JFrameFactory extends FrameFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(JFrameFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JFrame.class;
	}

	@UiAnnotationHandler(UiFrameCloseOperation.class)
	protected void handleUiFrameCloseOperation(FactoryContext factory,
			ComponentContext context, JFrame component,
			UiFrameCloseOperation annotate) {

		component.setDefaultCloseOperation(annotate.value().getSwingConstant());

		LOGGER.debug("{}|JFrame.setDefaultCloseOperation({})", context.getId(),
				annotate.value());
	}

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(FactoryContext factory,
			ComponentContext context, JFrame component, UiLayout annotate) {
		if (annotate.value() == NullLayout.class) {
			component.setLayout(null);
			return;
		}

		try {

			LayoutManager lm = annotate.value().newInstance();
			component.setLayout(lm);

			LOGGER.debug("{}|using layout {}", context.getId(), annotate
					.value());

		} catch (InstantiationException e) {
			LOGGER.error("UiLayout - InstantiationException - {} - {} ",
					annotate.value(), e.getMessage());
		} catch (IllegalAccessException e) {
			LOGGER.error("UiLayout - IllegalAccessException - {} - {} ",
					annotate.value(), e.getMessage());
		}
	}
}
