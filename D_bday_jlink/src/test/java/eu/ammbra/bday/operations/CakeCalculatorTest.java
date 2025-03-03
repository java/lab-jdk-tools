package eu.ammbra.bday.operations;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import eu.ammbra.bday.details.Cake;

class CakeCalculatorTest {

	@Test
	public void testEvaluateDimensions_AttendeesLessThanOrEqualTo10() {
		String flavor = "Chocolate";
		int attendees = 5;

		Cake cake = CakeCalculator.evaluateDimensions(flavor, attendees);

		assertEquals(flavor, cake.flavor());
		assertEquals(2.5, cake.weight(), 0.001);
		assertEquals(2, cake.layers(), 2);
	}

	@Test
	public void testEvaluateDimensions_AttendeesGreaterThan10() {
		String flavor = "Vanilla";
		int attendees = 15;

		Cake cake = CakeCalculator.evaluateDimensions(flavor, attendees);

		assertEquals(flavor, cake.flavor());
		assertEquals(6.25, cake.weight(), 0.001);
		assertEquals(7, cake.layers(), 2);
	}

	@Test
	public void testEvaluateDimensions_AttendeesZero() {
		String flavor = "Strawberry";
		int attendees = 0;

		assertThrows(IllegalArgumentException.class, () -> CakeCalculator.evaluateDimensions(flavor, attendees));

	}

	@Test
	public void testEvaluateDimensions_AttendeesNegative() {
		String flavor = "Red Velvet";
		int attendees = -20;

		assertThrows(IllegalArgumentException.class, () -> CakeCalculator.evaluateDimensions(flavor, attendees));
	}

	@Test
	public void testEvaluateDimensions_NullFlavor() {
		String flavor = null;
		int attendees = 10;

		Cake cake = CakeCalculator.evaluateDimensions(flavor, attendees);

		assertEquals(flavor, cake.flavor());
		assertEquals(5, cake.weight(), 0.001);
		assertEquals(2, cake.layers(), 2);
	}
}