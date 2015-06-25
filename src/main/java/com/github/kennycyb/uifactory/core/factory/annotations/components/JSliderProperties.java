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
package com.github.kennycyb.uifactory.core.factory.annotations.components;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.kennycyb.uifactory.core.factory.enums.Orientation;

/**
 * 
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface JSliderProperties {

	/**
	 * Sets the slider's minimum value to minimum.
	 * 
	 * @return
	 */
	int minimum() default 0;

	/**
	 * Sets the slider's maximum value to <i>maximum</i>.
	 * 
	 * @return
	 */
	int maximum() default 100;

	int value() default 0;

	Orientation orientation() default Orientation.HORIZONTAL;

	boolean inverted() default false;
}
