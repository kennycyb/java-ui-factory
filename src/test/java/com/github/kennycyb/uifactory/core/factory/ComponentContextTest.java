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
package com.github.kennycyb.uifactory.core.factory;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Test class for ComponentContext
 *
 * @since 1.0
 */
public class ComponentContextTest extends TestCase {

	@Test
	public void testAddChildWithUniqueIds() {
		final ComponentContext parent = new ComponentContext();
		parent.setId("parent");

		final ComponentContext child1 = new ComponentContext();
		child1.setId("child1");

		final ComponentContext child2 = new ComponentContext();
		child2.setId("child2");

		// Should succeed - different IDs
		parent.addChild(child1);
		parent.addChild(child2);

		assertEquals(2, parent.getChildren().size());
	}

	@Test
	public void testAddChildWithDuplicateId() {
		final ComponentContext parent = new ComponentContext();
		parent.setId("parent");

		final ComponentContext child1 = new ComponentContext();
		child1.setId("button");

		final ComponentContext child2 = new ComponentContext();
		child2.setId("button"); // Duplicate ID

		// First child should be added successfully
		parent.addChild(child1);
		assertEquals(1, parent.getChildren().size());

		// Second child with same ID should throw exception
		try {
			parent.addChild(child2);
			fail("Expected IllegalArgumentException for duplicate component ID");
		} catch (IllegalArgumentException e) {
			// Expected exception
			assertTrue(e.getMessage().contains("Duplicate component ID detected"));
			assertTrue(e.getMessage().contains("button"));
			assertTrue(e.getMessage().contains("parent"));
		}

		// Verify first child was not replaced
		assertEquals(1, parent.getChildren().size());
	}

	@Test
	public void testAddChildWithDuplicateIdHelpfulMessage() {
		final ComponentContext parent = new ComponentContext();
		parent.setId("myForm");

		final ComponentContext child1 = new ComponentContext();
		child1.setId("textField");

		final ComponentContext child2 = new ComponentContext();
		child2.setId("textField");

		parent.addChild(child1);

		try {
			parent.addChild(child2);
			fail("Expected IllegalArgumentException for duplicate component ID");
		} catch (IllegalArgumentException e) {
			// Verify error message contains helpful information
			String message = e.getMessage();
			assertTrue("Error message should mention duplicate ID", message.contains("textField"));
			assertTrue("Error message should mention parent component", message.contains("myForm"));
			assertTrue("Error message should suggest using @UiName", message.contains("@UiName"));
		}
	}

	@Test
	public void testAddChildWithNullContext() {
		final ComponentContext parent = new ComponentContext();
		parent.setId("parent");

		try {
			parent.addChild(null);
			fail("Expected IllegalArgumentException for null context");
		} catch (IllegalArgumentException e) {
			assertTrue("Error message should mention null", 
					e.getMessage().contains("null"));
		}
	}

	@Test
	public void testAddChildWithNullId() {
		final ComponentContext parent = new ComponentContext();
		parent.setId("parent");

		final ComponentContext child1 = new ComponentContext();
		child1.setId(null); // Null ID

		final ComponentContext child2 = new ComponentContext();
		child2.setId(null); // Another null ID

		// Should succeed - null IDs don't trigger duplicate check
		parent.addChild(child1);
		parent.addChild(child2);

		assertEquals(2, parent.getChildren().size());
	}
}
