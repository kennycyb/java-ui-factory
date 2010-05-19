package com.wpl.ui.annotations.constraints;

import java.awt.FlowLayout;

public @interface UiFlowLayoutConstraint {

	int value() default FlowLayout.LEFT;
}
