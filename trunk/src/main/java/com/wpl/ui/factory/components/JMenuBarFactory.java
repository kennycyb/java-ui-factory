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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.annotations.UiFont;
import com.wpl.ui.annotations.UiResource;
import com.wpl.ui.events.MenuBarEvent;
import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.UiAnnotationHandler;
import com.wpl.ui.factory.components.menu.MenuBarInfo;
import com.wpl.ui.factory.components.menu.MenuInfo;
import com.wpl.ui.factory.components.menu.MenuItemInfo;
import com.wpl.ui.factory.components.menu.MenuItemType;

public class JMenuBarFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory
			.getLogger(JMenuBarFactory.class);

	@Override
	protected Class<?> defaultType() {
		return JMenuBar.class;
	}

	@UiAnnotationHandler(UiResource.class)
	protected void handlerUiResource(final ComponentContext context,
			final JMenuBar component, UiResource annotate) {

		InputStream in = this.getClass().getClassLoader().getResourceAsStream(
				annotate.value());

		if (in == null) {
			LOGGER.error("resource {} not found", annotate.value());
			return;
		}

		final Object listener = context.getActionListener();
		final MenuBarInfo menuInfo = JAXB.unmarshal(in, MenuBarInfo.class);
		final UiFont uiFont = context.getAnnotatedElement().getAnnotation(
				UiFont.class);

		Font font = null;
		if (uiFont != null) {

			font = new Font(uiFont.name(), uiFont.style().getSwingConstant(),
					uiFont.size());
		}

		final Method onClicked = context.getActionListeners().get("clicked");

		for (MenuInfo menuItemInfo : menuInfo.getMenu()) {
			JMenu menu = new JMenu(menuItemInfo.getText());
			component.add(menu);

			if (font != null) {
				menu.setFont(font);
			}

			for (MenuItemInfo item : menuItemInfo.getMenuItem()) {
				if (item.getType() == MenuItemType.SEPARATOR) {
					menu.addSeparator();
					continue;
				}

				final JMenuItem mi = new JMenuItem(item.getText());
				if (font != null) {
					mi.setFont(font);
				}
				mi.setActionCommand(item.getId());
				mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (onClicked == null) {
							return;
						}

						final MenuBarEvent event = new MenuBarEvent();
						event.setSourceId(e.getActionCommand());

						onClicked.setAccessible(true);

						try {
							onClicked.invoke(listener, event);
						} catch (IllegalArgumentException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				menu.add(mi);
			}
		}

	}
}
