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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiName;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.UiSize;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.UiType;
import com.wpl.ui.factory.annotations.button.UiDefaultButton;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.annotations.frame.UiFrameResizable;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;

@UiText("Sample Frame with Inner Classes")
@UiSize(height = 600, width = 800)
@UiLayout(BorderLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiFrameResizable
public class SampleFrameWithInnerClass extends JFrame {

	@UiLayout(BorderLayout.class)
	class ContentPanel extends JPanel {

		@UiLayout(FlowLayout.class)
		class NorthContent extends JPanel {

			@UiLayout(BorderLayout.class)
			class NorthInnerContent extends JPanel {

				@UiText("north")
				@UiBorderLayoutConstraint(BorderLayoutConstraint.NORTH)
				JButton northButton;

				@UiText("south")
				@UiBorderLayoutConstraint(BorderLayoutConstraint.SOUTH)
				JButton southButton;
			}

			@UiText("LabelX")
			JLabel labelX;

			NorthInnerContent c;
		}

		@UiLayout(FlowLayout.class)
		class SouthContent extends JPanel {

			@UiText("southLabel")
			JLabel southLabel;

			@UiText("southButton1")
			JButton southButton1;
		}

		@UiBorderLayoutConstraint(BorderLayoutConstraint.NORTH)
		NorthContent n;

		@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
		@UiScrollable
		@UiText("HELLO WORLD\n")
		JTextArea content;

		@UiBorderLayoutConstraint(BorderLayoutConstraint.SOUTH)
		SouthContent south;
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
	void onStart_actionPerformed(ActionEvent e) {
		mCenter.content.append("onStart_actionPerformed\n");
	}

	/**
	 * As @AutoWired - this method will be called when "stop" button is called.
	 */
	void onStop_actionPerformed(ActionEvent e) {
		mCenter.content.append("onStop_actionPerformed\n");
	}

	void onSouthButton1_actionPerformed(ActionEvent e) {
		mCenter.content.append("onSouthButton1_actionPerformed\n");
	}

	void onNorthButton_actionPerformed(ActionEvent e) {
		mCenter.content.append("onNorthButton_actionPerformed\n");
	}

	void onSouthButton_actionPerformed(ActionEvent e) {
		mCenter.content.append("onSouthButton_actionPerformed\n");
	}

	public static void main(String[] args) {
		SwingFactory.create(SampleFrameWithInnerClass.class).setVisible(true);
	}
}
