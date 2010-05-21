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
package com.wpl.ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXB;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wpl.ui.factory.components.menu.MenuInfo;
import com.wpl.ui.factory.components.menu.MenuBarInfo;
import com.wpl.ui.factory.components.menu.MenuItemInfo;

public class JAXBTest {
	private static Logger LOGGER = LoggerFactory.getLogger(JAXBTest.class);

	@Test
	public void write() throws IOException {

		MenuBarInfo object = new MenuBarInfo();
		object.setId("mainMenu");

		MenuInfo item = new MenuInfo();
		item.setText("File");
		item.getMenuItem().add(new MenuItemInfo("Open"));

		object.addItem(item);

		FileOutputStream out = new FileOutputStream("/home/kenny/jaxbtest.xml");
		JAXB.marshal(object, out);
		out.close();

		FileInputStream in = new FileInputStream("/home/kenny/jaxbtest.xml");

		MenuBarInfo read = JAXB.unmarshal(in, MenuBarInfo.class);
		Assert.assertEquals("mainMenu", read.getId());
	}
}
