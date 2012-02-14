package com.wpl.ui.samples.layouts;

import javax.swing.JTextField;

import com.wpl.ui.factory.annotations.layouts.View;

@View(title = "AutoLayout sample", members = "|Hello|{input}"

)
public class AutoLayout {

	JTextField input;
}
