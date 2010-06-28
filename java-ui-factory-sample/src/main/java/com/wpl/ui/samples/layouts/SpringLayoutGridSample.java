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
package com.wpl.ui.samples.layouts;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiColumns;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiRows;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.constraints.UiSpringGridConstraint;
import com.wpl.ui.factory.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.annotations.frame.UiWindowPosition;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.SpringGridType;
import com.wpl.ui.factory.enums.WindowPosition;

/**
 * 
 * @since 1.0
 */
@UiText("Sample - SpringLayout - GRID")
@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiSpringGridConstraint(gridType = SpringGridType.GRID)
public class SpringLayoutGridSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(SpringLayoutGridSample.class);

	@UiText("Host:")
	JLabel label1;

	@UiText("127.0.0.1")
	@UiColumns(20)
	JTextField mHost;

	@UiText("Port:")
	JLabel label2;

	@UiText("21")
	@UiColumns(5)
	JTextField mPort;

	@UiText("Message:")
	JLabel label4;

	@UiScrollable
	@UiRows(5)
	@UiText("ABC\nDEF\nHIJ\n")
	JTextArea textarea;

	public static void main(String[] args) {
		SwingFactory.create(SpringLayoutGridSample.class).setVisible(true);
	}
}
