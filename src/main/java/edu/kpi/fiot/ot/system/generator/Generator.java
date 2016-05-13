package edu.kpi.fiot.ot.system.generator;

/**
 * The interface that represents the generator of random integer numbers.
 */
public interface Generator {
	
	/**
	 * Generates the random integer.
	 * 
	 * @return random integer.
	 */
	int getNextInteger();
	
	/**
	 * Generates random integers.
	 * 
	 * @return random integers.
	 */
	int[] getNextIntegers(int intCount);
}
