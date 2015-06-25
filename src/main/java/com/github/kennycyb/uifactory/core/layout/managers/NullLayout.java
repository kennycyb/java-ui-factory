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
package com.github.kennycyb.uifactory.core.layout.managers;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * This class is for @UiLayout. When using a NullLayoutManager using @UiLayout, @UiLocation
 * must be used to specify the location of the component, and @UiSize to specify
 * the size of a component.
 */
public class NullLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(final String arg0, final Component arg1) {
	}

	@Override
	public void layoutContainer(final Container arg0) {
	}

	@Override
	public Dimension minimumLayoutSize(final Container arg0) {
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(final Container arg0) {
		return null;
	}

	@Override
	public void removeLayoutComponent(final Component arg0) {
	}
}
