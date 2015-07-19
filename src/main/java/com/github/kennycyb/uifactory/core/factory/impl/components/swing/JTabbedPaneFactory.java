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

import javax.swing.JTabbedPane;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.tabbed.UiTabLayoutPolicy;
import com.github.kennycyb.uifactory.core.factory.annotations.tabbed.UiTabPlacement;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;

/**
 * ChangeLog:
 *
 * 0.5 - support ext, refactor with java8 lambda syntax
 *
 * 0.2 - new
 *
 *
 * @since 0.2
 */
public class JTabbedPaneFactory extends JComponentFactory {

	@Override
	public void initialize(final ComponentContext context) {

		super.initialize(context);

		context.addPostInit(() -> {

			final JTabbedPane tabPane = (JTabbedPane) context.getComponent();

			for (final ComponentContext child : context.getChildren()) {
				if (child.getComponent() == null || child.getComponent() == null) {
					continue;
				}

				final String title = child.getId();
				tabPane.add(title, child.getEnclosedComponent());
			}

		});
	}

	@UiAnnotationHandler(UiTabPlacement.class)
	void handleJSliderProperties(final ComponentContext context, final JTabbedPane component, final UiTabPlacement annotate) {
		component.setTabPlacement(annotate.value().getSwingConstant());
	}

	@UiAnnotationHandler(UiTabLayoutPolicy.class)
	void handleJSliderProperties(final ComponentContext context, final JTabbedPane component, final UiTabLayoutPolicy annotate) {
		component.setTabPlacement(annotate.value().getSwingConstant());
	}

}
