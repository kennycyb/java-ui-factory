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
package com.wpl.ui.ext;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * A dynamic list component.
 * 
 * @author Kenny Chong
 */
public class MutableList extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4585011537995224281L;

	private final DefaultListModel mListModel;

	public MutableList() {
		super(new DefaultListModel());
		this.mListModel = (DefaultListModel) this.getModel();
	}

	/**
	 * Append an element at the end of the list
	 * 
	 * @param e
	 * @since 1.0
	 */
	public void addElement(Object e) {
		this.mListModel.addElement(e);
	}

	/**
	 * Remove an element.
	 * 
	 * @param e
	 * @since 1.0
	 */
	public void removeElement(Object e) {
		this.mListModel.removeElement(e);
	}

	/**
	 * remove all elements.
	 * 
	 * @since 1.0
	 */
	public void removeAllElements() {
		this.mListModel.removeAllElements();
	}

	/**
	 * Retrieve an element from given index.
	 * 
	 * @param index
	 * @return
	 * @since 1.0
	 */
	public Object getElementAt(int index) {
		return this.mListModel.get(index);
	}
}
