package tools;

import config.Settings;
import functions.GeneralFunction;

import java.lang.reflect.MalformedParametersException;

public class MiscTools {

	private MiscTools(){}

	/**
	 * Returns n factorial (n!)
	 * @param n the number
	 * @return n!
	 */
	public static long factorial(int n) {
		if (n < 0)
			throw new UnsupportedOperationException("Cannot take the factorial of a negative number.");
		else if (n <= 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * Returns the location of a {@link GeneralFunction} in its class-based sort order (see {@link GeneralFunction#sortOrder})
	 * @param function the function whose class order is to be found
	 * @return location in {@link GeneralFunction#sortOrder}
	 */
	public static int findClassValue(GeneralFunction function) {
		Class<?> functionClass = function.getClass();
		for (int i = 0; i < GeneralFunction.sortOrder.length; i++) {
			if (GeneralFunction.sortOrder[i].isAssignableFrom(functionClass))
				return i;
		}
		throw new UnsupportedOperationException("Class " + function.getClass().getSimpleName() + " not supported.");
	}

	/**
	 * Parses a string to a boolean using the following rules, ignoring case:
	 * TRUE:  true, t, 1, yes, y
	 * FALSE: false, f, 0, no, n
	 * @param s the string to be parsed
	 * @return the string parsed to a boolean
	 */
	public static boolean parseBoolean(String s) {
		return switch (s.toLowerCase()) {
			case "true", "t", "1", "yes", "y" -> true;
			case "false", "f", "0", "no", "n" -> false;
			default -> throw new MalformedParametersException(s + " cannot be parsed to a boolean");
		};
	}

	/**
	 * Converts a double within {@link Settings#integerMargin} of an integer to an integer
	 * @param d the double to be converted
	 * @return the double rounded to an integer
	 * @throws IllegalArgumentException if the double is not within {@link Settings#integerMargin} of an integer
	 */
	public static int toInteger(double d) throws IllegalArgumentException{
		if (isAlmostInteger(d))
			return (int) (d + .5);
		else
			throw new IllegalArgumentException("Double " + d + " is not within " + Settings.zeroMargin + " of an integer.");
	}

	/**
	 * Checks if a double is within Settings.integerMargin of an integer
	 * @param d the double to be converted
	 * @return true if the double is within {@link Settings#integerMargin} of an integer
	 */
	public static boolean isAlmostInteger(double d) throws IllegalArgumentException{
		return Math.abs(((int) (d + .5)) - d) < Settings.integerMargin;
	}

    /**
     * Converts the string to a character,
     * @param input the string containing the character
     * @return the character in the string
     * @throws IllegalArgumentException if the input is not one character or three characters in the format 'c'
     */
    public static char getCharacter(String input) {
        if (input.length() == 1)
            return input.charAt(0);
        else if (input.length() == 3 && input.charAt(0) == '\'' && input.charAt(2) == '\'')
            return input.charAt(1);
        else
            throw new IllegalArgumentException("Input length should be 1 for Parser.toCharacter");
    }
}
