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

import java.awt.Frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;

public class FrameFactory extends WindowFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(FrameFactory.class);

	protected void title(ComponentContext context, Frame frame, String title) {
		frame.setTitle(title);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Frame.setTitle({})", context.getId(), title);
		}
	}

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(ComponentContext context, Frame frame,
			UiText annotate) {
		title(context, frame, annotate.value());
	}

	@UiAnnotationHandler(UiFrameResizable.class)
	protected void handleUiResizable(ComponentContext context, Frame frame,
			UiFrameResizable annotate) {
		frame.setResizable(annotate.value());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Frame.setResizable({})", context.getId(), annotate
					.value());
		}
	}
}
