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
package com.wpl.ui.factory.enums;

import javax.swing.JList;

/**
 * 
 * @since 1.0
 */
public enum ListLayoutOrientation {

	HORIZONTAL_WRAP(JList.HORIZONTAL_WRAP),

	VERTICAL_WRAP(JList.VERTICAL_WRAP),

	VERTICAL(JList.VERTICAL);

	private int mSwingContant;

	private ListLayoutOrientation(final int swingConstant) {
		mSwingContant = swingConstant;
	}

	public int getSwingContant() {
		return mSwingContant;
	}
}
