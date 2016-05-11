package edu.kpi.fiot.ot.test;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import edu.kpi.fiot.ot.system.System;

public class SystemFairnessIndexChart extends AbstractTestChart {
	
	@Override
	public Chart getChart(SimulationClass simula) {
		XYChart templateChart = getTemplateChart("System Fairness Index Chart", "User count", "Fairness Index");
		
		double[] userCounts = simula.getUserCounts();
		double[] userRRFairnesses = getSystemChannelFairness(simula.getRrsWithoutFramework());
		double[] userServiceRRFairnesses = getSystemChannelFairness(simula.getRrsWithFramework());
		double[] userMTFairnesses = getSystemChannelFairness(simula.getMtsWithoutFramework());
		double[] userServiceMTFairnesses = getSystemChannelFairness(simula.getMtsWithFramework());
		//double[] userPFFairnesses = getSystemChannelFairness(simula.getPfsWithoutFramework());
		//double[] userServicePFFairnesses = getSystemChannelFairness(simula.getPfsWithFramework());
		
		templateChart.addSeries("RR without framework", userCounts, userRRFairnesses);
		templateChart.addSeries("RR with framework", userCounts, userServiceRRFairnesses);
		templateChart.addSeries("MT without framework", userCounts, userMTFairnesses);
		templateChart.addSeries("MT with framework", userCounts, userServiceMTFairnesses);
		//templateChart.addSeries("PF without framework", userCounts, userPFFairnesses);
		//templateChart.addSeries("PF with framework", userCounts, userServicePFFairnesses);
		
		return templateChart;
	}
	
	private double[] getSystemChannelFairness(System[] systems) {
		double[] result = new double[systems.length];
		
		for (int i = 0; i < result.length; i++) {
			System system = systems[i];
			
			result[i] = system.getFairnessIndex();
		}

		return result;
	}	
}
