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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since 1.0
 */
public class CustomJButton extends JButton {

	private class MyBevelBorder extends AbstractBorder {

		public MyBevelBorder() {
		}

		@Override
		public void paintBorder(final Component c, final Graphics g,
				final int x, final int y, final int width, final int height) {

			final Graphics2D g2d = (Graphics2D) g;

			int red = 1;
			int gre = 22;
			int blu = 58;

			final int red2 = 58;
			final int gre2 = 91;
			final int blu2 = 117;

			final int redStep = (red2 - red) / 10;
			final int greStep = (gre2 - gre) / 10;
			final int bluStep = (blu2 - blu) / 10;

			Color color = new Color(red, gre, blu);

			for (int i = 0; i < 10; i++) {
				g2d.setColor(color);

				red += redStep;
				gre += greStep;
				blu += bluStep;

				color = new Color(red, gre, blu);

				if (is3DEffect()) {
					g2d.draw3DRect(x + i, y + i, width - (i * 2), height
							- (i * 2), true);
				} else {
					g2d.draw(new Rectangle(x + i, y + i, width - (i * 2),
							height - (i * 2)));
				}
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(CustomJButton.class);

	private boolean m3DEffect = true;

	public CustomJButton() {
		setPreferredSize(new Dimension(160, 80));
		setFont(new Font("Arial", Font.BOLD, 16));
		setBackground(new Color(5, 37, 62));
		setForeground(new Color(224, 255, 200));
		setBorder(new CompoundBorder(new LineBorder(Color.RED, 5),
				new MyBevelBorder()));
	}

	public boolean is3DEffect() {
		return m3DEffect;
	}

	public void set3DEffect(final boolean value) {
		m3DEffect = value;
	}

}
