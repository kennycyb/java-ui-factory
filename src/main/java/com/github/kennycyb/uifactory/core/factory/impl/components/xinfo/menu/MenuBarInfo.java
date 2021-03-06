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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class MenuBarInfo {

	public MenuBarInfo() {
	}

	private String mId;

	@XmlAttribute
	public String getId() {
		return mId;
	}

	public void setId(final String id) {
		mId = id;
	}

	private final List<MenuInfo> mItems = new ArrayList<MenuInfo>();

	@XmlElements({ @XmlElement(type = MenuInfo.class) })
	public List<MenuInfo> getMenu() {
		return mItems;
	}

	public void addItem(final MenuInfo item) {
		if (item == null) {
			return;
		}

		mItems.add(item);
	}
}
