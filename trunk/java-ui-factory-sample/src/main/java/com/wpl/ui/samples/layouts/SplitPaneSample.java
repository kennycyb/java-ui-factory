package com.wpl.ui.samples.layouts;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiScrollable;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.components.JSplitPaneProperties;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.annotations.layouts.SplitPaneContent;
import com.wpl.ui.enums.BorderLayoutConstraint;
import com.wpl.ui.enums.FrameCloseOperation;
import com.wpl.ui.enums.ScrollBarPolicy;
import com.wpl.ui.enums.SplitPaneOrientation;
import com.wpl.ui.enums.SplitPanePosition;
import com.wpl.ui.factory.SwingFactory;

@UiText("JSplitPane Sample")
@UiSize(height = 600, width = 800)
@UiLayout(BorderLayout.class)
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiFrameResizable
public class SplitPaneSample extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JSplitPaneProperties(orientation = SplitPaneOrientation.HORIZONTAL)
	@UiBorderLayoutConstraint(BorderLayoutConstraint.CENTER)
	JSplitPane splitPane;

	@SplitPaneContent(splitPaneName = "splitPane", position = SplitPanePosition.LEFT)
	JTree left;

	@SplitPaneContent(splitPaneName = "splitPane", position = SplitPanePosition.RIGHT)
	@UiScrollable(horizontal = ScrollBarPolicy.ALWAYS, vertical = ScrollBarPolicy.ALWAYS)
	JTextArea right;

	public static void main(String[] args) {
		SwingFactory.create(SplitPaneSample.class).setVisible(true);
	}
}
