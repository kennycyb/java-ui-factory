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

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.NullLayout;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

public class JFrameFactory extends ComponentFactory {

	private static Logger LOGGER = LoggerFactory.getLogger(JFrameFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JFrame.class;
	}

	@UiAnnotationHandler(UiFrameResizable.class)
	protected void handleUiResizable(ComponentContext context, JFrame frame,
			UiFrameResizable resizable) {
		frame.setResizable(resizable.value());
		LOGGER.debug("(JFrame){}.setResizable({})", context.getId(), resizable
				.value());
	}

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(ComponentContext context, JFrame frame,
			UiText text) {
		frame.setTitle(text.value());
		LOGGER.debug("(JFrame){}.setTitle({})", context.getId(), text.value());
	}

	@UiAnnotationHandler(UiLayout.class)
	protected void handleUiLayout(ComponentContext context, JFrame component,
			UiLayout layout) {
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

	@UiAnnotationHandler(UiFrameCloseOperation.class)
	protected void handleUiFrameCloseOperation(ComponentContext context,
			JFrame frame, UiFrameCloseOperation fco) {
		switch (fco.value()) {

		case EXIT:
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			LOGGER
					.debug(
							"(JFrame){}.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)",
							context.getId());
			break;

		case DISPOSE:
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			LOGGER
					.debug(
							"(JFrame){}.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)",
							context.getId());
			break;

		case HIDE:
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			LOGGER
					.debug(
							"(JFrame){}.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE)",
							context.getId());
			break;

		default:
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			LOGGER
					.debug(
							"(JFrame){}.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE)",
							context.getId());
			break;

		}
	}

}
