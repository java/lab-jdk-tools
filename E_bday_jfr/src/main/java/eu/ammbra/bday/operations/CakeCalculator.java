package eu.ammbra.bday.operations;

import eu.ammbra.bday.details.Cake;

import java.util.Random;


///Utility class for calculating cake weight based on the number of attendees.
///
public class CakeCalculator {

	/// Weight in kg for a single attendee
	private static final double BASE_CAKE_WEIGHT = 0.5;

	/// Extra weight in kg per person after 10 attendees
	private static final double EXTRA_WEIGHT_PER_PERSON = 0.25;


	///Calculates the weight of a cake based on the number of attendees.
	///
	///@param flavor the flavor of the cake
	///@param attendees the number of attendees
	///@return the calculated cake weight in kilograms
	///
	public static Cake evaluateDimensions(String flavor, int attendees) {
		Random random = new Random();
		switch (attendees) {
			case int i  when i <= 0 -> throw new IllegalArgumentException("Attendees must be greater than 0");
			case int at when at <= 10 -> {
				double weight = at * BASE_CAKE_WEIGHT;
				return new Cake(flavor, random.nextInt(1, 4), weight);
			}
			case int at ->  {
				double weight = (10 * BASE_CAKE_WEIGHT) + ((at - 10) * EXTRA_WEIGHT_PER_PERSON);
				return new Cake(flavor, random.nextInt(5, 8), weight);
			}
		}
	}
}
