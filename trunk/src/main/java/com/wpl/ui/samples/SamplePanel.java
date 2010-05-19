package com.wpl.ui.samples;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;

@UiLayout(BorderLayout.class)
public class SamplePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @UiBorderLayoutConstraint(BorderLayout.CENTER)
    @UiText("HELLO WORLD")
    private JTextArea mTextArea;

}
