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
package com.github.kennycyb.uifactory.core.factory.impl.components.juf;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.components.ILabel;

/**
 * 
 * @since 1.0
 */
public class ULabel extends UComponent<JLabel> implements ILabel {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(ULabel.class);

	@Override
	public void setText(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getComponent().setText(text);
			}
		});
	}

	public void setIcon(final Icon icon) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getComponent().setIcon(icon);
			}
		});
	}
}
