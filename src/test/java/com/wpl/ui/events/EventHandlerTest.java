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
package com.wpl.ui.events;

import junit.framework.TestCase;

import org.jmock.Mockery;
import org.junit.Test;

/**
 *
 * @since 1.0
 */
public class EventHandlerTest extends TestCase {

	class Listener {

	}

	final Mockery context = new Mockery();

	@Test
	public void testAddListenerWithNull() {
		final EventHandler<Listener> handler = new EventHandler<Listener>();
		handler.addListener(null);
	}

	@Test
	public void testRemoveListener() {
	}

	@Test
	public void testInvoke() {
	}
}
