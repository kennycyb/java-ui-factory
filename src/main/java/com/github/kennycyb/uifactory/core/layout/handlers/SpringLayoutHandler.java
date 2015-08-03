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
package com.github.kennycyb.uifactory.core.layout.handlers;

import java.awt.Component;
import java.awt.Container;

import javax.swing.RootPaneContainer;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.constraints.UiSpringGridConstraint;
import com.github.kennycyb.uifactory.core.factory.enums.SpringGridType;
import com.github.kennycyb.uifactory.core.utils.SpringUtilities;

/**
 *
 * @since 1.0
 */
public class SpringLayoutHandler implements ILayoutHandler {

	@Override
	public void layoutComponent(final ComponentContext componentContext) {

		final Object enclosed = componentContext.getEnclosedComponent();
		if (enclosed instanceof Component) {
			componentContext.getContainer().add((Component)enclosed);
		}
	}

	@Override
	public void finalLayout(final ComponentContext containerContext) {

		Container container = null;
		if (containerContext.getComponent() instanceof RootPaneContainer) {
			container = ((RootPaneContainer)containerContext.getComponent()).getContentPane();
		} else {
			container = (Container)containerContext.getComponent();
		}

		final UiSpringGridConstraint constraint = containerContext.getAnnotatedElement().getAnnotation(UiSpringGridConstraint.class);

		if (constraint == null) {

			final int rows = container.getComponentCount() / 2;
			final int cols = 2;

			SpringUtilities.makeCompactGrid(container, rows, cols, 5, 5, 5, 5);
			return;
		}

		final int cols = constraint.cols() <= 0 ? 2 : constraint.cols();
		final int rows = constraint.rows() <= 0 ? container.getComponentCount() / cols : constraint.rows();

		if (constraint.gridType() == SpringGridType.COMPACT) {
			SpringUtilities.makeCompactGrid(container, rows, cols, constraint.initialX(), constraint.initialY(), constraint.xPad(), constraint.yPad());
			return;
		}

		SpringUtilities.makeGrid(container, rows, cols, constraint.initialX(), constraint.initialY(), constraint.xPad(), constraint.yPad());
	}
}
