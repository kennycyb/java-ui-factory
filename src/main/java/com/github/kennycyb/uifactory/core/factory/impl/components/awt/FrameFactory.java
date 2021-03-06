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
package com.github.kennycyb.uifactory.core.factory.impl.components.awt;

import java.awt.Frame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiText;
import com.github.kennycyb.uifactory.core.factory.annotations.frame.UiFrameResizable;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;

/**
 * 
 * @author kenny
 * @since 1.0
 */
public class FrameFactory extends WindowFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FrameFactory.class);

	protected void title(final ComponentContext context, final Frame frame,
			final String title) {
		frame.setTitle(title);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Frame.setTitle({})", context.getId(), title);
		}
	}

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(final ComponentContext context,
			final Frame frame, final UiText annotate) {
		title(context, frame, annotate.value());
	}

	@UiAnnotationHandler(UiFrameResizable.class)
	protected void handleUiResizable(final ComponentContext context,
			final Frame frame, final UiFrameResizable annotate) {
		frame.setResizable(annotate.value());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|Frame.setResizable({})", context.getId(),
					annotate.value());
		}
	}
}
