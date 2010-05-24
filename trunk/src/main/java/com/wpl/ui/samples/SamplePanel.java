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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiEnabled;
import com.wpl.ui.annotations.UiIcon;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.UiText;

@UiLayout(FlowLayout.class)
public class SamplePanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@UiText("JTextArea")
	@UiColumns(20)
	@UiRows(5)
	private JTextArea mTextArea;

	@UiText("JCheckBox")
	@UiEnabled(false)
	private JCheckBox mCheckBox;

	@UiText("JCheckBox with Icon")
	@UiIcon("iMac.icon")
	private JCheckBox mCheckBoxWithIcon;

	@UiText("JTextField")
	private JTextField mTextField;

	@UiText("JLabel")
	private JLabel mLabel;

	@UiIcon("iMac.icon")
	@UiText("JLabel with Icon")
	private JLabel mLabelWithIcon;

	private JComboBox mComboBox;

	public static void main(String[] args) {
		UiFactory factory = new UiFactory();
		SamplePanel panel = factory.createContainer(SamplePanel.class);
		JFrame frame = new JFrame();
		frame.setTitle("Sample Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
