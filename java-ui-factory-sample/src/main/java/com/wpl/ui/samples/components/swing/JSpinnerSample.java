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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.components.spinner.SpinnerIntegerModelProperties;
import com.wpl.ui.factory.annotations.constraints.UiSpringGridConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

/**
 * 
 * @since 1.0
 */
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, title = "JSpinnerSample", windowPosition = WindowPosition.CENTER)
@UiLayout(SpringLayout.class)
@UiSpringGridConstraint(cols = 2)
public class JSpinnerSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(JSpinnerSample.class);

	@UiText("Spinner")
	JLabel label1;

	@SpinnerIntegerModelProperties(minimum = 0, maximum = 100, stepSize = 5, value = 0)
	JSpinner integerSpinner;

	public static void main(String[] args) {

		LOGGER.debug("Application Start");

		SwingFactory.create(JSpinnerSample.class).setVisible(true);
	}
}
