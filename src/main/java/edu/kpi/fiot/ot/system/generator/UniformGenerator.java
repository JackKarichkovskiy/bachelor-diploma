package edu.kpi.fiot.ot.system.generator;

import java.util.Random;

public class UniformGenerator implements Generator {

	private double minIntensity;
	
	private double maxIntensity;
	
	private Random ran = new Random(400);
	
	public UniformGenerator(double minIntensity, double maxIntensity) {
		super();
		this.minIntensity = minIntensity;
		this.maxIntensity = maxIntensity;
	}

	@Override
	public int getNextInteger() {
		int maxTime = (int) (1 / minIntensity);
		int minTime = (int) (1 / maxIntensity);
		int diff = maxTime - minTime;
		int timeInterval = minTime + ran.nextInt(diff);
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
