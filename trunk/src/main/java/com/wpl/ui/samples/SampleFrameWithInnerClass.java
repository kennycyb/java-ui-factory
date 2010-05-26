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
package com.wpl.ui.samples;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiAutoWired;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.annotations.button.UiDefaultButton;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.enums.FrameCloseOperation;

// FIXME: inner class

//Title of this frame
@UiText("Sample Frame")
// Size of this frame
@UiSize(height = 600, width = 800)
// Using BorderLayout
@UiLayout(BorderLayout.class)
// Close when frame is closed.
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiFrameResizable
@UiAutoWired
public class SampleFrameWithInnerClass extends JFrame {

	@UiLayout(BorderLayout.class)
	public static class ContentPanel extends JPanel {

		@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
		@UiText("HELLO WORLD\n")
		JTextArea content;
	}

	@UiLayout(FlowLayout.class)
	class CommandPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * - Default button. - Display text is "Start" - ID of this component is
		 * "start"
		 */
		@SuppressWarnings("unused")
		@UiDefaultButton
		@UiName("start")
		@UiText("Start")
		private JButton mStart;

		@SuppressWarnings("unused")
		@UiText("Stop")
		private JButton stop;
	}

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@UiType(ContentPanel.class)
	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	ContentPanel mCenter;

	@UiType(CommandPanel.class)
	@UiBorderLayoutConstraint(BorderLayoutConstraint.SOUTH)
	JPanel mSouth;

	/**
	 * As @AutoWired - this method will be called when "start" button is called.
	 */
	private void onStart_actionPerformed(ActionEvent e) {
		mCenter.content.append("start button clicked\n");
	}

	/**
	 * As @AutoWired - this method will be called when "stop" button is called.
	 */
	private void onStop_actionPerformed(ActionEvent e) {
		mCenter.content.append("stop button clicked\n");
	}

	public static void main(String[] args) {
		UiFactory.instance().createComponent(SampleFrameWithInnerClass.class)
				.setVisible(true);
	}
}
