/*
 * Manual test to verify duplicate component ID detection.
 * 
 * This class demonstrates how the fix prevents duplicate component IDs
 * from causing silent failures.
 */
package com.github.kennycyb.uifactory.core.factory;

public class DuplicateIdManualTest {

	public static void main(String[] args) {
		System.out.println("Testing duplicate component ID detection...\n");

		// Test 1: Adding children with unique IDs (should succeed)
		System.out.println("Test 1: Adding children with unique IDs");
		try {
			ComponentContext parent1 = new ComponentContext();
			parent1.setId("parent1");

			ComponentContext child1 = new ComponentContext();
			child1.setId("button1");

			ComponentContext child2 = new ComponentContext();
			child2.setId("button2");

			parent1.addChild(child1);
			parent1.addChild(child2);

			System.out.println("✓ SUCCESS: Added two children with unique IDs");
			System.out.println("  Parent: " + parent1.getId());
			System.out.println("  Children: " + parent1.getChildren().size());
		} catch (Exception e) {
			System.out.println("✗ FAILED: " + e.getMessage());
		}

		System.out.println("\n---\n");

		// Test 2: Adding children with duplicate IDs (should fail)
		System.out.println("Test 2: Adding children with duplicate IDs (should throw exception)");
		try {
			ComponentContext parent2 = new ComponentContext();
			parent2.setId("myForm");

			ComponentContext child1 = new ComponentContext();
			child1.setId("submitButton");

			ComponentContext child2 = new ComponentContext();
			child2.setId("submitButton"); // Duplicate!

			parent2.addChild(child1);
			System.out.println("  First child added: " + child1.getId());

			parent2.addChild(child2); // This should throw exception
			System.out.println("✗ FAILED: Should have thrown exception for duplicate ID");

		} catch (IllegalArgumentException e) {
			System.out.println("✓ SUCCESS: Exception thrown as expected");
			System.out.println("  Error message: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("✗ FAILED: Wrong exception type: " + e.getClass().getName());
		}

		System.out.println("\n---\n");

		// Test 3: Verify error message contains helpful information
		System.out.println("Test 3: Verifying error message quality");
		try {
			ComponentContext parent3 = new ComponentContext();
			parent3.setId("loginForm");

			ComponentContext child1 = new ComponentContext();
			child1.setId("username");

			ComponentContext child2 = new ComponentContext();
			child2.setId("username");

			parent3.addChild(child1);
			parent3.addChild(child2);

			System.out.println("✗ FAILED: Should have thrown exception");

		} catch (IllegalArgumentException e) {
			String message = e.getMessage();
			boolean hasChildId = message.contains("username");
			boolean hasParentId = message.contains("loginForm");
			boolean hasSuggestion = message.contains("@UiName");

			System.out.println("✓ Error message validation:");
			System.out.println("  - Contains child ID 'username': " + hasChildId);
			System.out.println("  - Contains parent ID 'loginForm': " + hasParentId);
			System.out.println("  - Contains @UiName suggestion: " + hasSuggestion);

			if (hasChildId && hasParentId && hasSuggestion) {
				System.out.println("✓ SUCCESS: Error message contains all expected information");
			} else {
				System.out.println("✗ PARTIAL: Error message missing some information");
			}
		}

		System.out.println("\n=== All manual tests completed ===");
	}
}
