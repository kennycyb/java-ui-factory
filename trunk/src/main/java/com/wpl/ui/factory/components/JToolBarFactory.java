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

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.factory.components.toolbar.ToolbarInfo;
import com.wpl.ui.factory.components.toolbar.ToolbarItemInfo;
import com.wpl.ui.listeners.MethodListenerProxy;

public class JToolBarFactory extends JComponentFactory {

	private static Logger LOGGER = LoggerFactory
			.getLogger(JToolBarFactory.class);

	@UiAnnotationHandler(UiResource.class)
	void handleUiResource(final ComponentContext context,
			final JToolBar component, final UiResource annotate) {

		final InputStream xml = getClass().getClassLoader()
				.getResourceAsStream(annotate.value());

		if (xml == null) {
			return;
		}

		MethodListenerProxy<ActionListener> actionListenerProxy = new MethodListenerProxy<ActionListener>(
				ActionListener.class, context.getActionListeners());

		MethodListenerProxy<ItemListener> itemListenerProxy = new MethodListenerProxy<ItemListener>(
				ItemListener.class, context.getActionListeners());

		final ToolbarInfo info = JAXB.unmarshal(xml, ToolbarInfo.class);
		for (final ToolbarItemInfo item : info.getItem()) {

			Component childComponent = null;

			switch (item.getType()) {
			case SEPARATOR:
				component.addSeparator();
				break;

			case BUTTON:
				final JButton button = new JButton();

				if (item.getIcon() != null) {
					final URL iconUrl = getClass().getClassLoader()
							.getResource(item.getIcon());

					final Icon image = new ImageIcon(iconUrl);

					button.setIcon(image);
				}
				button.setText(item.getText());
				button.setActionCommand(item.getId());

				if (actionListenerProxy.hasListeningMethod()) {
					button.addActionListener(actionListenerProxy.getProxy());
				}

				component.add(button);

				childComponent = button;

				break;

			case CHECKBOX:
				final JCheckBox check = new JCheckBox(item.getText());
				check.setActionCommand(item.getId());
				component.add(check);

				if (itemListenerProxy.hasListeningMethod()) {
					check.addItemListener(itemListenerProxy.getProxy());
				}

				childComponent = check;
				break;

			case COMBOBOX:
				final JComboBox combo = new JComboBox(item.getComboBox()
						.getItem().toArray());
				component.add(combo);

				if (itemListenerProxy.hasListeningMethod()) {
					combo.addItemListener(itemListenerProxy.getProxy());
				}

				if (actionListenerProxy.hasListeningMethod()) {
					combo.addActionListener(actionListenerProxy.getProxy());
				}

				childComponent = combo;
				break;
			}

			if (childComponent != null) {
				ComponentContext child = new ComponentContext();
				child.setDeclared(false);
				child.setId(item.getId());
				child.setComponent(childComponent);
				child.setParentContext(context);
				context.addChild(child);
			}
		}
	}
}
