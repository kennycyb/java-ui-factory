package com.wpl.ui.ext.button;

import java.lang.reflect.Field;

import javax.swing.JRadioButton;


public final class EnumRadioButtons<E extends Enum<?>> extends RadioButtons {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7344509985982715108L;
	
	
	public EnumRadioButtons(Class<E> enumClass) {
		
		Field[] fields = enumClass.getDeclaredFields();
		
		for (Field field : fields) {

			if (!field.isEnumConstant()) {
				continue;
			}
			
			field.setAccessible(true);
			
			try {
				Enum<?> e = (Enum<?>)field.get(null);
				JRadioButton button = new JRadioButton();
				button.setText(e.name());
				this.addRadioButton(button);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				continue;
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				continue;
			}
		}		
	}
}
