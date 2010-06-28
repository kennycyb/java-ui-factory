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
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiInit;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.annotations.constraints.UiFlowLayoutConstraint;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FlowLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;

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

	private static Logger LOGGER = LoggerFactory.getLogger(PictureViewer.class);

	public class Viewer extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Viewer() {
			super(null, true);
		}

		private BufferedImage mSource;

		private Graphics2D mG2d;
		private VolatileImage mVolatiledImage;

		private Image mScaled;

		protected void checkVolatiledImage() {
		}

		protected void prepareNonVolatiled() {
			if (mSource == mSourceImage) {
				return;
			}

			mSource = mSourceImage;

			long start = System.currentTimeMillis();

			mScaled = mSource.getScaledInstance(getWidth(), getHeight(),
					Image.SCALE_FAST);

			LOGGER.debug("Time for prepare image: {}", System
					.currentTimeMillis()
					- start);
		}

		private int width, height;

		protected void prepare() {

			if (mSource == mSourceImage) {
				return;
			}

			long start = System.currentTimeMillis();

			mSource = mSourceImage;

			if (mVolatiledImage == null || width != getWidth()
					|| height != getHeight()) {

				width = getWidth();
				height = getHeight();

				GraphicsEnvironment ge = GraphicsEnvironment
						.getLocalGraphicsEnvironment();
				GraphicsConfiguration gc = ge.getDefaultScreenDevice()
						.getDefaultConfiguration();

				mVolatiledImage = gc.createCompatibleVolatileImage(width,
						height);

				mG2d = mVolatiledImage.createGraphics();
			}

			do {

				Graphics2D g = mG2d;

				try {
					// g = mVolatiledImage.createGraphics();

					g.drawImage(mSource, 0, 0, getWidth(), getHeight(), 0, 0,
							mSource.getWidth(), mSource.getHeight(), this);

				} finally {
					// It's always best to dispose of your Graphics objects.
					// g.dispose();
				}

				mScaled = mVolatiledImage;

			} while (mVolatiledImage.contentsLost());

			LOGGER.debug("Time for prepare image: {}", System
					.currentTimeMillis()
					- start);
		}

		@Override
		protected void paintComponent(final Graphics g) {

			// prepare();

			prepareNonVolatiled();

			long start = System.currentTimeMillis();

			g.drawImage(mScaled, 0, 0, this);

			LOGGER.debug("Time taken in paintComponent: {}", System
					.currentTimeMillis()
					- start);
		}
	}

	private BufferedImage mSourceImage;

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

		long start = System.currentTimeMillis();

		if (++mCurrentIndex >= mImages.size()) {
			mCurrentIndex = 0;
		}

		setImage(mImages.get(mCurrentIndex));

		LOGGER.debug("onNext taken: {}", System.currentTimeMillis() - start);
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

				long start = System.currentTimeMillis();

				mCurrentIndex = 0;

				while (doSlideShow) {
					onNext_actionPerformed(e);
					if (mCurrentIndex == 0 || mCurrentIndex == 10) {
						break;
					}
					try {
						Thread.sleep(10);
					} catch (final InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				LOGGER.debug("Slide Show Time: {}", System.currentTimeMillis()
						- start);
			}
		}).start();

	}

	public PictureViewer() {
	}

	@UiInit
	void init() {
		// final File dir = new File("docs/samples");

		File dir = new File("/media/Backup/Backup/My Pictures/Wedding/ALL");
		final File[] files = dir.listFiles();

		for (final File f : files) {
			if (f.getName().endsWith(".jpg") || f.getName().endsWith(".png")) {
				mImages.add(f.getAbsolutePath());
			}
		}

		if (mImages.size() == 0) {
			return;
		}

		setImage(mImages.get(0));
	}

	public BufferedImage loadFromFile(String file) throws IOException {

		return ImageIO.read(new File(file));
	}

	public void setImage(final String file) {

		long start = System.currentTimeMillis();

		try {
			mSourceImage = loadFromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		LOGGER.debug("Time to load: {}", System.currentTimeMillis() - start);

		start = System.currentTimeMillis();

		viewer.repaint();

		LOGGER.debug("Time to repaint: {}", System.currentTimeMillis() - start);
	}

	public static void main(final String[] args) {

		final PictureViewer form = SwingFactory.create(PictureViewer.class);
		form.setVisible(true);
	}
}
