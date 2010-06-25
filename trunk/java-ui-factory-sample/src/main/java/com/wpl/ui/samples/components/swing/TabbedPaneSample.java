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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiAddComponentTo;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.components.JTextFieldProperties;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.frame.UiWindowPosition;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;

/**
 * 
 * @since 1.0
 */
@UiWindowPosition(WindowPosition.CENTER)
@UiSize(height = 600, width = 800)
@UiLayout(BorderLayout.class)
public class TabbedPaneSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory
			.getLogger(TabbedPaneSample.class);

	public static class Tab1 extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@JTextFieldProperties(text = "Tab1")
		JTextField field1;

	}

	private class Tab2 extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		@UiText("HELLO")
		JLabel field2;
	}

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	JTabbedPane tabbedPane;

	@UiName("Tab 1")
	@UiAddComponentTo("tabbedPane")
	Tab1 tab1;

	@UiName("Tab 2")
	@UiAddComponentTo("tabbedPane")
	Tab2 tab2;

	public static void main(final String[] args) {
		SwingFactory.create(TabbedPaneSample.class).setVisible(true);
	}
}
