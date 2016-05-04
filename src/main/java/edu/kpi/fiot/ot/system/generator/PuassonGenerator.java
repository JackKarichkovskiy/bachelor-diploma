package edu.kpi.fiot.ot.system.generator;

public class PuassonGenerator implements Generator {

	private final double intensity;
	
	private final int timeInterval;
	
	public PuassonGenerator(double intensity) {
		super();
		this.intensity = intensity;
		this.timeInterval = (int) (1 / intensity);
	}

	@Override
	public int getNextInteger() {
		return timeInterval;
	}

	@Override
	public int[] getNextIntegers(int intCount) {
		int[] result = new int[intCount];
		for (int i = 0; i < result.length; i++) {
			result[i] = getNextInteger();
		}
		return result;
	}

}
