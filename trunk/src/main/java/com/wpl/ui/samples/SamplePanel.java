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
import java.text.DateFormat;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiColumns;
import com.wpl.ui.annotations.UiEchoChar;
import com.wpl.ui.annotations.UiEnabled;
import com.wpl.ui.annotations.UiIcon;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiRows;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.UiTextFormat;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.annotations.constraints.UiSpringGridConstraint;
import com.wpl.ui.annotations.frame.UiWindowPosition;
import com.wpl.ui.enums.WindowPosition;

@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiSpringGridConstraint(cols = 2)
public class SamplePanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@UiText("JTextArea")
	private JLabel mLabel1;

	@UiText("JTextArea")
	@UiColumns(20)
	@UiRows(5)
	private JTextArea mTextArea;

	@UiText("JCheckBox")
	private JLabel mLable2;

	@UiText("JCheckBox")
	@UiEnabled(false)
	private JCheckBox mCheckBox;

	@UiText("JCheckBox with Icon")
	private JLabel mLable3;

	@UiText("JCheckBox with Icon")
	@UiIcon("iMac.icon")
	private JCheckBox mCheckBoxWithIcon;

	@UiText("JTextField")
	private JLabel mLable4;

	@UiText("JTextField")
	private JTextField mTextField;

	@UiText("JFormattedTextField")
	private JLabel mLable5;

	@UiType(JFormattedTextField.class)
	@UiTextFormat(formatter = DateFormat.class, pattern = "dd MMM yyyy")
	@UiText("10 MAY 2010")
	private JTextField mFormattedDateField;

	@UiText("JPasswordField")
	private JLabel mlabel9;

	@UiType(JPasswordField.class)
	@UiEchoChar('?')
	private JTextField mPasswordField;

	@UiText("JLable with Icon")
	private JLabel mLable6;

	@UiIcon("iMac.icon")
	@UiText("JLabel with Icon")
	private JLabel mLabelWithIcon;

	@UiText("JComboBox")
	private JLabel mLable7;
	private JComboBox mComboBox;

	@UiText("JRadioButton")
	private JLabel mLabel8;
	private JRadioButton mRadioButton;

	public static void main(String[] args) {
		UiFactory factory = new UiFactory();
		SamplePanel panel = factory.createComponent(SamplePanel.class);
		JFrame frame = new JFrame();
		frame.setTitle("Sample Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
