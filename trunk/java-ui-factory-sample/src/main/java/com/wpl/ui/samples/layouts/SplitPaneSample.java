package com.wpl.ui.samples.layouts;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;

import com.wpl.ui.factory.SwingFactory;
import com.wpl.ui.factory.annotations.UiLayout;
import com.wpl.ui.factory.annotations.UiScrollable;
import com.wpl.ui.factory.annotations.UiSize;
import com.wpl.ui.factory.annotations.UiText;
import com.wpl.ui.factory.annotations.components.JSplitPaneProperties;
import com.wpl.ui.factory.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.factory.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.factory.annotations.frame.UiFrameResizable;
import com.wpl.ui.factory.annotations.layouts.SplitPaneContent;
import com.wpl.ui.factory.enums.BorderLayoutConstraint;
import com.wpl.ui.factory.enums.FrameCloseOperation;
import com.wpl.ui.factory.enums.ScrollBarPolicy;
import com.wpl.ui.factory.enums.SplitPaneOrientation;
import com.wpl.ui.factory.enums.SplitPanePosition;

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
