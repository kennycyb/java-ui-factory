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
package com.github.kennycyb.uifactory.core.factory.enums;

import javax.swing.JTabbedPane;

/**
 *
 * @since 1.0
 */
public enum TabLayoutPolicy {

	WRAP(JTabbedPane.WRAP_TAB_LAYOUT),

	SCROLL(JTabbedPane.SCROLL_TAB_LAYOUT);

	private int mSwingConstant;

	private TabLayoutPolicy(final int swingConstant) {
		mSwingConstant = swingConstant;
	}

	public int getSwingConstant() {
		return mSwingConstant;
	}
}
