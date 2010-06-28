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

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiColumns;
import com.wpl.ui.factory.annotations.UiEchoChar;
import com.wpl.ui.factory.annotations.UiEnabled;
import com.wpl.ui.factory.annotations.UiIcon;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiRows;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.UiTextFormat;
import com.wpl.ui.factory.annotations.UiType;
import com.wpl.ui.factory.annotations.constraints.UiSpringGridConstraint;
import com.wpl.ui.factory.annotations.frame.UiWindowPosition;
import com.wpl.ui.factory.enums.WindowPosition;

@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiSpringGridConstraint(cols = 2)
public class SamplePanel extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@UiText("JTextArea")
	JLabel mLabel1;

	@UiText("JTextArea")
	@UiColumns(20)
	@UiRows(5)
	JTextArea mTextArea;

	@UiText("JCheckBox")
	JLabel mLable2;

	@UiText("JCheckBox")
	@UiEnabled(false)
	JCheckBox mCheckBox;

	@UiText("JCheckBox with Icon")
	JLabel mLable3;

	@UiText("JCheckBox with Icon")
	@UiIcon("iMac.icon")
	JCheckBox mCheckBoxWithIcon;

	@UiText("JTextField")
	JLabel mLable4;

	@UiText("JTextField")
	JTextField mTextField;

	@UiText("JFormattedTextField")
	JLabel mLable5;

	@UiType(JFormattedTextField.class)
	@UiTextFormat(formatter = DateFormat.class, pattern = "dd MMM yyyy")
	@UiText("10 MAY 2010")
	JTextField mFormattedDateField;

	@UiText("JPasswordField")
	JLabel mlabel9;

	@UiType(JPasswordField.class)
	@UiEchoChar('?')
	JTextField mPasswordField;

	@UiText("JLable with Icon")
	JLabel mLable6;

	@UiIcon("iMac.icon")
	@UiText("JLabel with Icon")
	JLabel mLabelWithIcon;

	@UiText("JComboBox")
	JLabel mLable7;
	JComboBox mComboBox;

	@UiText("JRadioButton")
	JLabel mLabel8;
	JRadioButton mRadioButton;

	public static void main(final String[] args) {
		final SamplePanel panel = SwingFactory.create(SamplePanel.class);
		final JFrame frame = new JFrame();
		frame.setTitle("Sample Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
