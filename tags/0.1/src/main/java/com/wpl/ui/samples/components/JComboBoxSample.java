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
package com.wpl.ui.samples.components;

import java.awt.FlowLayout;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.annotations.UiSimpleItems;
import com.wpl.ui.annotations.components.JFrameProperties;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;

@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, title = "JComboBoxSample", windowPosition = WindowPosition.CENTER)
@UiLayout(FlowLayout.class)
public class JComboBoxSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(JComboBoxSample.class);

	@UiSimpleItems( { "Male", "Female" })
	JComboBox simpleComboBox;

	@UiResource("JComboBoxSample-ComplexComboBox.xml")
	JComboBox complexComboBox;

	void onSimpleComboBox_itemStateChanged(final ItemEvent e) {
		LOGGER
				.debug(
						"onSimpleComboBox_itemStateChanged: {} ({})",
						e.getItem(),
						e.getStateChange() == ItemEvent.DESELECTED ? "DESELECTED"
								: e.getStateChange() == ItemEvent.ITEM_FIRST ? "ITEM_FIRST"
										: e.getStateChange() == ItemEvent.ITEM_LAST ? "ITEM_LAST"
												: e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED ? "ITEM_STATE_CHANGED"
														: e.getStateChange() == ItemEvent.SELECTED ? "SELECTED"
																: "UNKNOWN");
	}

	void onComplexComboBox_itemStateChanged(final ItemEvent e) {
		LOGGER
				.debug(
						"onComplexComboBox_itemStateChanged: {} ({})",
						e.getItem(),
						e.getStateChange() == ItemEvent.DESELECTED ? "DESELECTED"
								: e.getStateChange() == ItemEvent.ITEM_FIRST ? "ITEM_FIRST"
										: e.getStateChange() == ItemEvent.ITEM_LAST ? "ITEM_LAST"
												: e.getStateChange() == ItemEvent.ITEM_STATE_CHANGED ? "ITEM_STATE_CHANGED"
														: e.getStateChange() == ItemEvent.SELECTED ? "SELECTED"
																: "UNKNOWN");
	}

	public static void main(final String[] args) {
		SwingFactory.create(JComboBoxSample.class).setVisible(true);
	}
}
