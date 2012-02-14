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
package com.wpl.ui.samples.components.swing;

import javax.swing.JFrame;
import javax.swing.JList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.ext.MutableList;
import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiInit;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiResource;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.UiSimpleItems;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.ScrollBarPolicy;
import com.wpl.ui.factory.enums.WindowPosition;
import com.wpl.ui.layout.managers.VerticalFlowLayout;

/**
 * 
 * @since 1.0
 */
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, title = "JComboBoxSample", windowPosition = WindowPosition.CENTER)
@UiLayout(VerticalFlowLayout.class)
public class JListSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(JListSample.class);

	@UiSimpleItems({ "Monday", "Tuesday", "Wednesday" })
	JList simpleList;

	@UiResource("JListSample-List.xml")
	@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
	JList xmlList;

	MutableList mutableList;

	@UiInit
	void init() {
		mutableList.addElement("HELLO WORLD");
		mutableList.addElement("WHO");

	}

	public static void main(final String[] args) {
		SwingFactory.create(JListSample.class).setVisible(true);
	}
}
