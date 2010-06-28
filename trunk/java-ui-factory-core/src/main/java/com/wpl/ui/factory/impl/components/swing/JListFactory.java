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
package com.wpl.ui.factory.impl.components.swing;

import java.io.InputStream;

import javax.swing.JList;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.ComponentContext;
import com.wpl.ui.factory.annotations.UiResource;
import com.wpl.ui.factory.annotations.UiSimpleItems;
import com.wpl.ui.factory.impl.UiAnnotationHandler;
import com.wpl.ui.factory.impl.components.xinfo.list.ListInfo;

/**
 * 
 * @since 1.0
 */
public class JListFactory extends JComponentFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(JListFactory.class);

	/**
	 * 
	 * @since 1.0
	 * @param context
	 * @param component
	 * @param annotate
	 */
	@UiAnnotationHandler(UiSimpleItems.class)
	void handleUiSimpleItems(final ComponentContext context,
			final JList component, final UiSimpleItems annotate) {

		component.setListData(annotate.value());

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("{}|JList.setListData(count={})", context.getId(),
					annotate.value().length);
		}
	}

	/**
	 * 
	 * @since 1.0
	 * @param context
	 * @param component
	 * @param annotate
	 */
	@UiAnnotationHandler(UiResource.class)
	void handleUiResource(final ComponentContext context,
			final JList component, final UiResource annotate) {

		final InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream(annotate.value());

		if (in == null) {
			LOGGER.error("{}|resource {} not found", context.getId(),
					annotate.value());
			return;
		}

		final ListInfo info = JAXB.unmarshal(in, ListInfo.class);

		component.setListData(info.getItems().toArray());
		LOGGER.debug("{}|JList.setListData(count={})", context.getId(), info
				.getItems().size());
	}
}
