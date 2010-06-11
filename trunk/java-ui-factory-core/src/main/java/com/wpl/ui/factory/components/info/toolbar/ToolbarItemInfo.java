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
package com.wpl.ui.factory.components.info.toolbar;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.wpl.ui.factory.components.info.ComponentInfo;
import com.wpl.ui.factory.components.info.combobox.ComboBoxInfo;

/**
 * 
 * @since 1.0
 */
public class ToolbarItemInfo extends ComponentInfo {

	String mText;
	ToolbarItemType mType;
	ComboBoxInfo mComboBox;
	String mIcon;

	@XmlAttribute
	public String getIcon() {
		return mIcon;
	}

	public void setIcon(final String icon) {
		mIcon = icon;
	}

	@XmlAttribute
	public String getText() {
		return mText;
	}

	public void setText(final String text) {
		mText = text;
	}

	@XmlAttribute
	public ToolbarItemType getType() {
		return mType;
	}

	public void setType(final ToolbarItemType type) {
		mType = type;
	}

	@XmlElement
	public ComboBoxInfo getComboBox() {
		return mComboBox;
	}

	public void setComboBox(final ComboBoxInfo comboBox) {
		mComboBox = comboBox;
	}
}
