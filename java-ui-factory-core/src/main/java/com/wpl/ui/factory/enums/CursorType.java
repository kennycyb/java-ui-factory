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

import java.awt.Cursor;

/**
 * 
 * @since 1.0
 */
public enum CursorType {

	CROSSHAIR(Cursor.CROSSHAIR_CURSOR),

	CUSTOM(Cursor.CUSTOM_CURSOR),

	DEFAULT(Cursor.DEFAULT_CURSOR),

	EAST_RESIZE(Cursor.E_RESIZE_CURSOR),

	HAND(Cursor.HAND_CURSOR),

	MOVE(Cursor.MOVE_CURSOR),

	NORTH_RESIZE(Cursor.N_RESIZE_CURSOR),

	NORTH_EAST_RESIZE(Cursor.NE_RESIZE_CURSOR),

	NORTH_WEST_RESIZE(Cursor.NW_RESIZE_CURSOR),

	SOUTH_RESIZE(Cursor.S_RESIZE_CURSOR),

	SOUTH_EAST_RESIZE(Cursor.SE_RESIZE_CURSOR),

	SOUTH_WEST_RESIZE(Cursor.SW_RESIZE_CURSOR),

	TEXT(Cursor.TEXT_CURSOR),

	WEST(Cursor.W_RESIZE_CURSOR),

	WAIT(Cursor.WAIT_CURSOR),

	;

	private final int mSwingConstant;

	private CursorType(final int swingConstant) {
		mSwingConstant = swingConstant;
	}

	public int getSwingConstant() {
		return mSwingConstant;
	}
}
