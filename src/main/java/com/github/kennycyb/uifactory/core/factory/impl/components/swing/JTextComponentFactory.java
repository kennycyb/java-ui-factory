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
package com.github.kennycyb.uifactory.core.factory.impl.components.swing;

import java.awt.event.InputMethodListener;

import javax.swing.text.JTextComponent;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiEditable;
import com.github.kennycyb.uifactory.core.factory.annotations.UiText;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.listeners.MethodListenerProxy;

/**
 *
 * @author kenny
 * @since 1.0
 *
 */
public abstract class JTextComponentFactory extends JComponentFactory {

	@UiAnnotationHandler(UiText.class)
	protected void handleUiText(final ComponentContext context,
			final JTextComponent component, final UiText annotate) {
		component.setText(annotate.value());
	}

	@UiAnnotationHandler(UiEditable.class)
	protected void handleUiEditable(final ComponentContext context,
			final JTextComponent component, final UiEditable annotate) {
		component.setEditable(annotate.value());
	}

	@Override
	public void wireComponent(final ComponentContext context) {
		super.wireComponent(context);

		final JTextComponent component = (JTextComponent) context
				.getComponent();

		final MethodListenerProxy<InputMethodListener> inputMethodListenerProxy = new MethodListenerProxy<InputMethodListener>(
				InputMethodListener.class, context.getActionListeners());

		if (inputMethodListenerProxy.hasListeningMethod()) {
			component.addInputMethodListener(inputMethodListenerProxy
					.getProxy());
		}
	}
}
