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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.FactoryContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.factory.components.menu.MenuBarInfo;
import com.wpl.ui.factory.components.menu.MenuInfo;
import com.wpl.ui.factory.components.menu.MenuItemInfo;
import com.wpl.ui.factory.components.menu.MenuItemType;
import com.wpl.ui.listeners.MethodListener;

public class JMenuBarFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JMenuBarFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JMenuBar.class;
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

		final Object listener = factory.getObject();
		final MenuBarInfo menuInfo = JAXB.unmarshal(in, MenuBarInfo.class);
		final UiFont uiFont = context.getAnnotatedElement().getAnnotation(
				UiFont.class);

		LOGGER.debug("(JMenuBar){}.id={}", context.getId(), menuInfo.getId());

		Font font = null;
		if (uiFont != null) {

			font = new Font(uiFont.name(), uiFont.style().getSwingConstant(),
					uiFont.size());
		}

		final Method onClicked = context.getActionListeners().get("clicked");

		ActionListener onClickedListener = null;

		if (listener != null && onClicked != null) {

			final MethodListener<ActionEvent> methodListener = new MethodListener<ActionEvent>(
					listener, onClicked);

			onClickedListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					methodListener.invoke(e);
				}
			};

		}

		int menuIndex = -1;

		for (MenuInfo menuItemInfo : menuInfo.getMenu()) {

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

				final JMenuItem mi = new JMenuItem(item.getText());
				if (font != null) {
					mi.setFont(font);
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

				if (onClickedListener != null) {
					mi.addActionListener(onClickedListener);

					if (LOGGER.isDebugEnabled()) {
						LOGGER
								.debug(
										"wireComponent (JMenuItem){}.actionPerformed to {}",
										new Object[] { menuChild.getId(),
												onClicked.getName() });
					}
				}
				menu.add(mi);
			}
		}

	}
}
