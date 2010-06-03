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
package com.wpl.ui.factory.components;

import java.awt.event.ItemListener;
import java.io.InputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.annotations.UiSimpleItems;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.factory.components.combobox.ComboBoxInfo;
import com.wpl.ui.factory.components.combobox.ComboBoxItemInfo;
import com.wpl.ui.listeners.MethodListenerProxy;

public class JComboBoxFactory extends JComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(JComboBoxFactory.class);

	@UiAnnotationHandler(UiSimpleItems.class)
	protected void handleUiSimpleItems(ComponentContext context,
			JComboBox component, UiSimpleItems annotate) {
		component.setModel(new DefaultComboBoxModel(annotate.value()));
	}

	@UiAnnotationHandler(UiResource.class)
	protected void handleUiResource(ComponentContext context,
			JComboBox component, UiResource annotate) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(
				annotate.value());
		if (in == null) {
			LOGGER.error("UiResource {} not found", annotate.value());
			return;
		}

		ComboBoxInfo info = JAXB.unmarshal(in, ComboBoxInfo.class);
		if (info == null) {
			LOGGER.error("UiResource {} is invalid", annotate.value());
			return;
		}

		for (ComboBoxItemInfo itemInfo : info.getItem()) {
			component.addItem(itemInfo.getText());
		}
	}

	@Override
	public void wireComponent(ComponentContext context) {
		super.wireComponent(context);

		JComboBox cb = (JComboBox) context.getComponent();

		MethodListenerProxy<ItemListener> itemListenerProxy = new MethodListenerProxy<ItemListener>(
				ItemListener.class, context.getActionListeners());

		if (itemListenerProxy.hasListeningMethod()) {
			cb.addItemListener(itemListenerProxy.getProxy());
			LOGGER.debug("{}|JComboBox.addItemListener", context.getId());
		}
	}
}
