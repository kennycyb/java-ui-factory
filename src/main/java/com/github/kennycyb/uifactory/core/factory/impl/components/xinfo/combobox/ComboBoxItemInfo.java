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
package com.github.kennycyb.uifactory.core.factory.impl.components.xinfo.combobox;

import javax.xml.bind.annotation.XmlAttribute;

public class ComboBoxItemInfo {
	private String mId;
	private String mText;

	@XmlAttribute
	public String getId() {
		return mId;
	}

	public void setId(final String id) {
		mId = id;
	}

	@XmlAttribute
	public String getText() {
		return mText;
	}

	public void setText(final String text) {
		mText = text;
	}

	@Override
	public String toString() {
		return getText();
	}
}
