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

import java.awt.event.ItemListener;
import java.io.InputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.kennycyb.uifactory.core.factory.ComponentContext;
import com.github.kennycyb.uifactory.core.factory.annotations.UiResource;
import com.github.kennycyb.uifactory.core.factory.annotations.UiSimpleItems;
import com.github.kennycyb.uifactory.core.factory.impl.UiAnnotationHandler;
import com.github.kennycyb.uifactory.core.factory.impl.components.xinfo.combobox.ComboBoxInfo;
import com.github.kennycyb.uifactory.core.factory.impl.components.xinfo.combobox.ComboBoxItemInfo;
import com.github.kennycyb.uifactory.core.listeners.MethodListenerProxy;

/**
 *
 * @author kenny
 * @since 1.0
 */
public class JComboBoxFactory extends JComponentFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(JComboBoxFactory.class);

	@SuppressWarnings("rawtypes")
	@UiAnnotationHandler(UiSimpleItems.class)
	protected void handleUiSimpleItems(final ComponentContext context,
			final JComboBox component, final UiSimpleItems annotate) {
		component.setModel(new DefaultComboBoxModel(annotate.value()));
	}

	@UiAnnotationHandler(UiResource.class)
	protected void handleUiResource(final ComponentContext context,
			final JComboBox component, final UiResource annotate) {
		final InputStream in = getClass().getClassLoader().getResourceAsStream(
				annotate.value());
		if (in == null) {
			LOGGER.error("UiResource {} not found", annotate.value());
			return;
		}

		final ComboBoxInfo info = JAXB.unmarshal(in, ComboBoxInfo.class);
		if (info == null) {
			LOGGER.error("UiResource {} is invalid", annotate.value());
			return;
		}

		for (final ComboBoxItemInfo itemInfo : info.getItem()) {
			component.addItem(itemInfo.getText());
		}
	}

	@Override
	public void wireComponent(final ComponentContext context) {
		super.wireComponent(context);

		final JComboBox cb = (JComboBox) context.getComponent();

		final MethodListenerProxy<ItemListener> itemListenerProxy = new MethodListenerProxy<ItemListener>(
				ItemListener.class, context.getActionListeners());

		if (itemListenerProxy.hasListeningMethod()) {
			cb.addItemListener(itemListenerProxy.getProxy());
			LOGGER.debug("{}|JComboBox.addItemListener", context.getId());
		}
	}
}
