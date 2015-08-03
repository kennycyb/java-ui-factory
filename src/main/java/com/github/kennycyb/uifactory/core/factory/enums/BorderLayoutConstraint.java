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

import java.awt.BorderLayout;

/**
 * BorderLayoutConstraint.
 *
 * @since 0.2
 * @author kenny
 *
 */
public enum BorderLayoutConstraint {

	CENTER(BorderLayout.CENTER),

	NORTH(BorderLayout.NORTH),

	EAST(BorderLayout.EAST),

	WEST(BorderLayout.WEST),

	SOUTH(BorderLayout.SOUTH);

	private final String mSwingConstant;

	private BorderLayoutConstraint(final String swingConstant) {
		mSwingConstant = swingConstant;
	}

	public String getSwingConstant() {
		return mSwingConstant;
	}
}
