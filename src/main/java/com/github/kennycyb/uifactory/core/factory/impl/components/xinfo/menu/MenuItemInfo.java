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
package com.github.kennycyb.uifactory.core.factory.impl.components.xinfo.menu;

import javax.xml.bind.annotation.XmlAttribute;

public class MenuItemInfo {

	private String mId;
	private String mText;
	private MenuItemType mType = MenuItemType.MENU;
	private String mIcon;
	private String mRadioGroupId;

	private boolean mSelected;

	public MenuItemInfo() {
	}

	public MenuItemInfo(final String text) {
		mText = text;
	}

	@XmlAttribute
	public boolean isSelected() {
		return mSelected;
	}

	public void setSelected(final boolean selected) {
		mSelected = selected;
	}

	@XmlAttribute
	public String getRadioGroupId() {
		return mRadioGroupId;
	}

	public void setRadioGroupId(final String radioGroupId) {
		mRadioGroupId = radioGroupId;
	}

	@XmlAttribute
	public String getIcon() {
		return mIcon;
	}

	public void setIcon(final String icon) {
		mIcon = icon;
	}

	@XmlAttribute
	public String getId() {
		return mId;
	}

	public void setId(final String id) {
		mId = id;
	}

	public void setText(final String text) {
		mText = text;
	}

	@XmlAttribute
	public String getText() {
		return mText;
	}

	@XmlAttribute
	public MenuItemType getType() {
		return mType;
	}

	public void setType(final MenuItemType type) {
		mType = type;
	}
}
