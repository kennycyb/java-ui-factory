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
package com.wpl.ui.samples;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.wpl.ui.UiFactory;
import com.wpl.ui.annotations.UiLayout;
import com.wpl.ui.annotations.UiSize;
import com.wpl.ui.annotations.UiText;
import com.wpl.ui.annotations.UiType;
import com.wpl.ui.annotations.constraints.UiBorderLayoutConstraint;
import com.wpl.ui.annotations.frame.UiFrameCloseOperation;
import com.wpl.ui.annotations.frame.UiFrameResizable;
import com.wpl.ui.enums.FrameCloseOperation;

/**
 * 
 */
//Title of this frame
@UiText("Sample Frame")
// Size of this frame
@UiSize(height = 800, width = 800)
// Using BorderLayout
@UiLayout(BorderLayout.class)
// Close when frame is closed.
@UiFrameCloseOperation(FrameCloseOperation.EXIT)
// This frame is resizable
@UiFrameResizable
public class SampleFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Add a panel into the center of this Frame.
     * 
     * - Create the panel from SamplePanel (which is inherit from JPanel)
     * - add this panel into center of this Frame.
     */
    @UiType(SamplePanel.class)
    @UiBorderLayoutConstraint(BorderLayout.CENTER)
    private JPanel mCenter;

    public static void main(String[] args) {
        UiFactory factory = new UiFactory();
        JFrame sample = factory.createFrame(SampleFrame.class);

        sample.setVisible(true);
    }
}
