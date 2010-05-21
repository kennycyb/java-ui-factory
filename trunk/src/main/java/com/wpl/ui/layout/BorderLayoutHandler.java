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
package com.wpl.ui.layout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.AnnotatedElement;

import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;

public class BorderLayoutHandler implements ILayoutHandler {

	public boolean handleComponent(Container container, Component component,
			AnnotatedElement annotate) {
		UiBorderLayoutConstraint constraint = annotate
				.getAnnotation(UiBorderLayoutConstraint.class);

		if (constraint == null) {
			return false;
		}

		switch (constraint.value()) {
		case CENTER:
			container.add(component, BorderLayout.CENTER);
			break;

		case EAST:
			container.add(component, BorderLayout.EAST);
			break;

		case NORTH:
			container.add(component, BorderLayout.NORTH);
			break;

		case SOUTH:
			container.add(component, BorderLayout.SOUTH);
			break;

		case WEST:
			container.add(component, BorderLayout.WEST);
			break;

		default:
			return false;
		}

		return true;
	}
}
