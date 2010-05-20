package com.wpl.ui.samples;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiAutoWired;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiName;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.enums.FrameCloseOperation;

//Title of this frame
@UiText("Sample Frame")
// Size of this frame
@UiSize(height = 600, width = 800)
// Using BorderLayout
@UiLayout(BorderLayout.class)
// Close when frame is closed.
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
@UiFrameResizable
@UiAutoWired
public class SampleFrameWithInnerClass extends JFrame {

    @UiLayout(BorderLayout.class)
    public class InnerPanel extends JPanel {

        @UiBorderLayoutConstraint(BorderLayout.CENTER)
        @UiText("HELLO WORLD")
        public JTextArea mContent;
    }

    @UiLayout(FlowLayout.class)
    public class CommandPanel extends JPanel {

        @UiName("start")
        @UiText("Start")
        private JButton mStart;

        @UiText("Stop")
        private JButton stop;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @UiType(InnerPanel.class)
    @UiBorderLayoutConstraint(BorderLayout.CENTER)
    private InnerPanel mCenter;

    @UiType(CommandPanel.class)
    @UiBorderLayoutConstraint(BorderLayout.SOUTH)
    private JPanel mSouth;

    private void onStart_clicked() {
        mCenter.mContent.append("start button clicked\n");
    }

    private void onStop_clicked() {
        mCenter.mContent.append("stop button clicked\n");
    }

    public static void main(String[] args) {
        UiFactory factory = new UiFactory();
        JFrame sample = factory.createFrame(SampleFrameWithInnerClass.class);

        sample.setVisible(true);
    }
}
