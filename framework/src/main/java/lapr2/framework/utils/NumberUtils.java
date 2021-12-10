package lapr2.framework.utils;

import java.util.Random;

/**
 * Represents a utility class with methods related to number operations and syntax.
 *
 * @author flow
 */
public class NumberUtils {

	/**
	 * Instance of the {@link Random} object.
	 * Stored so one does only need to initialize it one time.
	 */
	private static Random random = new Random();

	/**
	 * Creates an empty utility class.
	 * Utility classes, which are collections of static members, are not meant to be instantiated.
	 * Thus, the private constructor prevents the instantiation of the class outside it.
	 */
	private NumberUtils() {
	}

	/**
	 * Returns a random integer in the given range.
	 * For example, for <i>min = 2</i> and <i>max = 5</i>, returns 2, 3, 4 or 5.
	 *
	 * @param min the minimum number
	 * @param max the maximum number
	 * @return a random number
	 */
	public static int getRandomInt(int min, int max) {
		int realMax = (min > max) ? min : max;
		int realMin = (realMax == min) ? max : min;

		int randomIndex = random.nextInt(realMax - realMin + 1);
		return randomIndex + realMin;
	}
}
