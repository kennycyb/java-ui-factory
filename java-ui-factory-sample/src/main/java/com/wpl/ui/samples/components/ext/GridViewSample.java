package com.wpl.ui.samples.components.ext;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.ext.gridview.GridView;
import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.components.JFrameProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.annotations.frame.UiWindowPosition;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.WindowPosition;
import com.wpl.ui.samples.model.Person;

@JFrameProperties(frameCloseOperation = FrameCloseOperation.EXIT, height = 600, width = 800, title = "GridViewSample")
@UiWindowPosition(WindowPosition.CENTER)
@UiLayout(BorderLayout.class)
public class GridViewSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(GridViewSample.class);

	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	private GridView<Person> gridView;

	void onGridView_init() {
		LOGGER.debug("mainGridView_init");
		assert (gridView != null);

	}

	public static void main(final String[] args) {
		SwingFactory.create(GridViewSample.class).setVisible(true);
	}
}
