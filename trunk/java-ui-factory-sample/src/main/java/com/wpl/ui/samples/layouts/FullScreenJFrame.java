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

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiRows;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.annotations.frame.UiWindowPosition;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.ScrollBarPolicy;
import com.wpl.ui.factory.enums.WindowPosition;
import com.wpl.ui.layout.managers.VerticalFlowLayout;

/**
 * 
 * @since 1.0
 */
@UiLayout(VerticalFlowLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiText("Full Screen Sample")
@UiWindowPosition(WindowPosition.FULL)
public class FullScreenJFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory
			.getLogger(VerticalFlowLayoutSample.class);

	@UiText("line 1")
	JLabel line1;

	@UiText("line 2")
	JLabel line2;

	@UiText("text 3")
	JTextField text3;

	@UiText("check 4")
	JCheckBox check4;

	@UiScrollable(autoScroll = true, horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
	@UiText("=============== text area 5 ===============")
	@UiRows(5)
	JTextArea textarea5;

	public static void main(String[] args) {
		SwingFactory.create(FullScreenJFrame.class).setVisible(true);
	}
}
