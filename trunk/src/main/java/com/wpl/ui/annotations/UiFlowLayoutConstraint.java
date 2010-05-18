package com.wpl.ui.annotations;

import java.awt.FlowLayout;

public @interface UiFlowLayoutConstraint {

	int value() default FlowLayout.LEFT;
}
