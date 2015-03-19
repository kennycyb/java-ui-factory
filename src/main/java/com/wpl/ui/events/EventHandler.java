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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @since 1.0
 */
public class EventHandler<E> implements IEventHandler<E> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EventHandler.class);

	private final List<IEventListener<E>> mListeners = new ArrayList<IEventListener<E>>();

	public void addListener(final IEventListener<E> listener) {
		if (listener == null) {
			LOGGER.warn("Trying to add a null listener");
			return;
		}

		mListeners.add(listener);
		LOGGER.debug("added listener: {}", listener);
	}

	public void removeListener(final IEventListener<E> listener) {
		if (listener == null) {
			LOGGER.warn("Trying to remove a null listener");
			return;
		}

		mListeners.remove(listener);
		LOGGER.debug("removed listener: {}", listener);
	}

	public void invoke(final E args) {

		for (final IEventListener<E> listener : mListeners) {
			listener.invoke(args);
		}
	}
}
