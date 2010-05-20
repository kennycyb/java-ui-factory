package com.wpl.ui.factory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;

import com.wpl.ui.annotations.UiAutoWired;
import com.wpl.ui.annotations.UiInit;
import com.wpl.ui.annotations.UiPreInit;

public class FactoryInfo {

	/**
	 * The object that was being manufactured.
	 */
	private final Object mObject;

	/**
	 * Method to invoke before starting any manufacturing process.
	 */
	public Method mPreInitMethod;

	/**
	 * Method to invoke once manufacturing is done by the factory.
	 */
	public Method mInitMethod;

	private boolean mAutoWired = true;

	public FactoryInfo(Object object) {

		this.mObject = object;

		UiAutoWired autoWired = object.getClass().getAnnotation(
				UiAutoWired.class);
		mAutoWired = autoWired == null || autoWired.value();

		Method[] methods = object.getClass().getDeclaredMethods();

		for (Method m : methods) {
			if (m.getAnnotation(UiInit.class) != null) {
				this.mInitMethod = m;
				continue;
			}

			if (m.getAnnotation(UiPreInit.class) != null) {
				this.mPreInitMethod = m;
				continue;
			}

			String methodName = m.getName();

			if (methodName.startsWith("on")) {
				int action = methodName.indexOf('_');
				if (action > 0) {
					String componentName = methodName.substring(2, 3)
							.toLowerCase()
							+ methodName.substring(3, action);
					String actionName = methodName.substring(action + 1);

					ComponentInfo cInfo = this.mComponents.get(componentName);
					if (cInfo == null) {
						cInfo = new ComponentInfo(componentName);
						this.mComponents.put(componentName, cInfo);
					}

					cInfo.addActionListener(actionName, m);
				}
			}
		}

	}

	/**
	 * List of component that has been manufactured.
	 */
	public Map<String, ComponentInfo> mComponents = new HashMap<String, ComponentInfo>();

	public void onPreInit() {
		invoke(this.mPreInitMethod);
	}

	public void onInit() {
		invoke(this.mInitMethod);
	}

	public void wireComponents() {
		for (final ComponentInfo info : mComponents.values()) {
			for (final Map.Entry<String, Method> entry : info
					.getActionListeners().entrySet()) {
				if (entry.getKey().equals("clicked")) {
					if (info.getComponent() instanceof AbstractButton) {
						AbstractButton button = (AbstractButton) info
								.getComponent();
						button.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									entry.getValue().setAccessible(true);
									entry.getValue().invoke(
											FactoryInfo.this.mObject);
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
					}
				}
			}
		}
	}

	private void invoke(Method method) {
		if (method == null) {
			return;
		}

		method.setAccessible(true);

		try {
			method.invoke(this.mObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * True if auto-wired component -> actions, false otherwise. Default is true
	 * if @UiAutoWired is not declared.
	 * 
	 * @return
	 */

	public boolean isAutoWired() {
		return mAutoWired;
	}
}
