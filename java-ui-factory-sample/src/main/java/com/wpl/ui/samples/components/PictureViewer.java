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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.image.VolatileImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.components.JFrameProperties;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.constraints.UiFlowLayoutConstraint;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.enums.FlowLayoutConstraint;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.WindowPosition;
import com.wpl.ui.factory.SwingFactory;

/**
 * 
 * @since 1.0
 */
@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, height = 600, width = 800, windowPosition = WindowPosition.CENTER)
@UiLayout(BorderLayout.class)
public class PictureViewer extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private static Logger LOGGER = LoggerFactory.getLogger(PictureViewer.class);

	public class Viewer extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private VolatileImage mVolatileImage;

		private int width, height;

		public Viewer() {
			super(null, true);
		}

		private Image mSource;
		private Image mScaled;

		@Override
		protected void paintComponent(final Graphics g) {
			// super.paintComponent(g);

			if (mVolatileImage == null || width != getWidth()
					|| height != getHeight()) {
				width = getWidth();
				height = getHeight();
				mVolatileImage = createVolatileImage(width, height);
			}

			if (mSourceImage == null) {
				return;
			}

			if (mSource != mSourceImage) {
				mSource = mSourceImage;

				if (mSource == null) {
					return;
				}

				mScaled = mSource.getScaledInstance(width, height,
						Image.SCALE_FAST);
			}

			final Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_OFF);
			g2d.drawImage(mScaled, 0, 0, this);

			// final GraphicsConfiguration gc = getGraphicsConfiguration();
			// final int valCode = mVolatileImage.validate(gc);
			// if (valCode == VolatileImage.IMAGE_INCOMPATIBLE) {
			// mVolatileImage = createVolatileImage(width, height);
			// }
			//
			// final Graphics2D g2D = mVolatileImage.createGraphics();
			// g2D.drawImage(mScaled, 0, 0, this);
			// g2D.dispose();
			// g.drawImage(mVolatileImage, 0, 0, this);
		}
	}

	private Image mSourceImage;

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	Viewer viewer;

	@UiLayout(FlowLayout.class)
	@UiFlowLayoutConstraint(FlowLayoutConstraint.CENTER)
	class CommandButtons extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		JButton next;

		@UiText("Slide Show")
		JButton slideShow;
	}

	@UiBorderLayoutConstraint(BorderLayoutConstraint.SOUTH)
	CommandButtons buttons;

	List<String> mImages = new ArrayList<String>();
	int mCurrentIndex = 0;

	void onNext_actionPerformed(final ActionEvent e) {

		if (mImages.size() == 0) {
			return;
		}

		if (++mCurrentIndex >= mImages.size()) {
			mCurrentIndex = 0;
		}

		setImage(Toolkit.getDefaultToolkit().getImage(
				mImages.get(mCurrentIndex)));
	}

	volatile boolean doSlideShow = false;

	void onSlideShow_actionPerformed(final ActionEvent e) {

		if (doSlideShow) {
			doSlideShow = false;
			return;
		}

		doSlideShow = true;

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (doSlideShow) {
					onNext_actionPerformed(e);
					try {
						Thread.sleep(100);
					} catch (final InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}).start();

	}

	public PictureViewer() {
	}

	@UiInit
	void init() {
		final File dir = new File("docs/samples");
		final File[] files = dir.listFiles();

		for (final File f : files) {
			if (f.getName().endsWith(".png")) {
				mImages.add(f.getAbsolutePath());
			}
		}

		if (mImages.size() == 0) {
			return;
		}

		setImage(Toolkit.getDefaultToolkit().getImage(mImages.get(0)));
	}

	public void setImage(final Image image) {
		mSourceImage = image;
		viewer.repaint();
	}

	public static void main(final String[] args) {

		final PictureViewer form = SwingFactory.create(PictureViewer.class);
		form.setVisible(true);
	}
}
