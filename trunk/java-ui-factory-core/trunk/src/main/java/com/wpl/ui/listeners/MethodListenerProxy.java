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
package com.wpl.ui.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MethodListenerProxy<E> implements MethodInterceptor {
	private static Logger LOGGER = LoggerFactory
			.getLogger(MethodListenerProxy.class);

	Map<String, MethodListener<?>> mMethodListeners = new HashMap<String, MethodListener<?>>();
	private final E mProxy;

	public MethodListenerProxy(Class<E> listenerClass,
			Map<String, MethodListener<?>> methods) {

		mProxy = listenerClass.cast(Enhancer.create(listenerClass, this));

		for (Field f : listenerClass.getDeclaredFields()) {
			if (f.getType() != MethodListener.class) {
				continue;
			}

			MethodListener<?> m = methods.get(f.getName());
			if (m == null) {
				continue;
			}

			f.setAccessible(true);

			try {
				f.set(mProxy, m);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mMethodListeners.put(f.getName(), m);
		}

		for (Method m : listenerClass.getMethods()) {

			MethodListener<?> ml = methods.get(m.getName());

			if (ml == null) {
				continue;
			}

			mMethodListeners.put(m.getName(), ml);
		}

		if (LOGGER.isDebugEnabled()) {
			for (Map.Entry<String, MethodListener<?>> entry : mMethodListeners
					.entrySet()) {
				LOGGER.debug("{}|Listening on {}", entry.getValue()
						.getListenerName(), entry.getValue().getMethodName());
			}
		}
	}

	public E getProxy() {
		return mProxy;
	}

	public boolean hasListeningMethod() {
		return !mMethodListeners.isEmpty();
	}

	// ~ Implementation of MethodInterceptor -----------------------------------

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		MethodListener methodListener = mMethodListeners.get(method.getName());
		if (methodListener == null) {
			return null;
		}

		LOGGER.debug("invoke: {}", methodListener.getMethodName());
		// FIXME:
		methodListener.invoke(args[0]);
		return null;
	}
}
