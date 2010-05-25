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
package com.wpl.ui.samples.combo;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.annotations.UiSimpleItems;
import com.wpl.ui.annotations.frame.UiWindowPosition;
import com.wpl.ui.enums.WindowPosition;

@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(FlowLayout.class)
public class SampleComboBox extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(SampleComboBox.class);

	@UiSimpleItems( { "Male", "Female" })
	JComboBox simpleComboBox;

	@UiResource("SampleComboBox-ComplexComboBox.xml")
	JComboBox complexComboBox;

	void onSampleComboBox_windowClosing(WindowEvent e) {
		LOGGER.debug("onWithUiSimpleItems_windowClosing");
		System.exit(0);
	}

	void onSimpleComboBox_itemStateChanged(ItemEvent e) {
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

	void oncomplexComboBox_itemStateChanged(ItemEvent e) {
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

	public static void main(String[] args) {
		UiFactory factory = new UiFactory();
		factory.createFrame(SampleComboBox.class).setVisible(true);
	}
}
