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

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.annotations.frame.UiFrameMenu;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.factory.components.menu.MenuBarInfo;
import com.wpl.ui.factory.components.menu.MenuInfo;
import com.wpl.ui.factory.components.menu.MenuItemInfo;
import com.wpl.ui.factory.components.menu.MenuItemType;
import com.wpl.ui.listeners.MethodListenerProxy;

public class JMenuBarFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JMenuBarFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JMenuBar.class;
	}

	@UiAnnotationHandler(UiFrameMenu.class)
	protected void handleUiFrameMenu(FactoryContext factory,
			final ComponentContext context, final JMenuBar component,
			UiFrameMenu annotate) {

		if (factory.getObject() instanceof JFrame) {
			((JFrame) factory.getObject()).setJMenuBar(component);
			LOGGER.debug("added {} as JMenuBar in {}", context.getId(), factory
					.getObject().getClass().getSimpleName());
		}
	}

	@UiAnnotationHandler(UiResource.class)
	protected void handleUiResource(FactoryContext factory,
			final ComponentContext context, final JMenuBar component,
			UiResource annotate) {

		InputStream in = this.getClass().getClassLoader().getResourceAsStream(
				annotate.value());

		if (in == null) {
			LOGGER.error("resource {} not found", annotate.value());
			return;
		}

		final MenuBarInfo menuInfo = JAXB.unmarshal(in, MenuBarInfo.class);
		final UiFont uiFont = context.getAnnotatedElement().getAnnotation(
				UiFont.class);

		LOGGER.debug("(JMenuBar){}.id={}", context.getId(), menuInfo.getId());

		Font font = null;
		if (uiFont != null) {

			font = new Font(uiFont.name(), uiFont.style().getSwingConstant(),
					uiFont.size());
		}

		MethodListenerProxy<ActionListener> actionListenerProxy = new MethodListenerProxy<ActionListener>(
				factory.getObject(), context.getActionListeners(),
				ActionListener.class);

		MethodListenerProxy<ItemListener> itemListenerProxy = new MethodListenerProxy<ItemListener>(
				factory.getObject(), context.getActionListeners(),
				ItemListener.class);

		int menuIndex = -1;

		for (MenuInfo menuItemInfo : menuInfo.getMenu()) {

			Map<String, ButtonGroup> radioGroup = new HashMap<String, ButtonGroup>();

			menuIndex++;

			JMenu menu = new JMenu(menuItemInfo.getText());
			component.add(menu);

			if (menuItemInfo.getId() == null) {
				menuItemInfo.setId(menuItemInfo.getId());
			}

			ComponentContext child = factory.findComponentContext(menuItemInfo
					.getId());
			child.setId(menuItemInfo.getId());
			child.setComponent(menu);
			child.setDeclared(false);
			context.addChild(child);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("(JMenuBar){}.addMenu(id=\"{}\", menu=\"{}\")",
						new Object[] { context.getId(), menuItemInfo.getId(),
								menuItemInfo.getText() });
			}

			if (font != null) {
				menu.setFont(font);
			}

			int menuChildIndex = -1;

			for (MenuItemInfo item : menuItemInfo.getMenuItem()) {
				if (item.getType() == MenuItemType.SEPARATOR) {
					menu.addSeparator();
					continue;
				}

				menuChildIndex++;

				final JMenuItem mi;

				switch (item.getType()) {

				case CHECKBOX:
					mi = new JCheckBoxMenuItem(item.getText());
					if (item.isSelected()) {
						mi.setSelected(true);
					}
					break;

				case RADIOBUTTON:
					mi = new JRadioButtonMenuItem(item.getText());

					ButtonGroup group = radioGroup.get(item.getRadioGroupId());
					if (group == null) {
						group = new ButtonGroup();
						radioGroup.put(item.getRadioGroupId(), group);
					}
					if (item.isSelected()) {
						mi.setSelected(true);
					}
					group.add(mi);
					break;

				default:
					mi = new JMenuItem(item.getText());
					break;
				}

				if (font != null) {
					mi.setFont(font);
				}

				if (item.getIcon() != null) {
					URL url = getClass().getClassLoader().getResource(
							item.getIcon());
					if (url != null) {
						mi.setIcon(new ImageIcon(url));
					}
				}

				if (item.getId() == null) {
					item.setId(item.getText());
				}

				ComponentContext menuChild = factory.findComponentContext(item
						.getId());
				menuChild.setComponent(mi);
				child.addChild(menuChild);
				child.setDeclared(false);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(
							"(JMenuBar){}.{}.addItem(id=\"{}\", menu=\"{}\")",
							new Object[] { context.getId(),
									menuItemInfo.getId(), item.getId(),
									item.getText() });
				}

				mi.setActionCommand(item.getId());

				if (actionListenerProxy.hasListeningMethod()) {
					mi.addActionListener(actionListenerProxy.getProxy());
				}

				if (itemListenerProxy.hasListeningMethod()) {
					mi.addItemListener(itemListenerProxy.getProxy());
				}

				menu.add(mi);
			}
		}

	}
}
