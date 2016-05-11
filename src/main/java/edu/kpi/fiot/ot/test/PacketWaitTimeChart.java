package edu.kpi.fiot.ot.test;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import edu.kpi.fiot.ot.system.System;

public class PacketWaitTimeChart extends AbstractTestChart {
	
	@Override
	public Chart getChart(SimulationClass simula) {
		XYChart templateChart = getTemplateChart("Packet Wait Time Chart", "User count", "Total Wait Time");
		
		double[] userCounts = simula.getUserCounts();
		double[] userRRWaitTimes = getSystemChannelWaitTimes(simula.getRrsWithoutFramework());
		double[] userServiceRRWaitTimes = getSystemChannelWaitTimes(simula.getRrsWithFramework());
		double[] userMTWaitTimes = getSystemChannelWaitTimes(simula.getMtsWithoutFramework());
		double[] userServiceMTWaitTimes = getSystemChannelWaitTimes(simula.getMtsWithFramework());
		//double[] userPFWaitTimes = getSystemChannelWaitTimes(simula.getPfsWithoutFramework());
		//double[] userServicePFWaitTimes = getSystemChannelWaitTimes(simula.getPfsWithFramework());
		
		templateChart.addSeries("RR without framework", userCounts, userRRWaitTimes);
		templateChart.addSeries("RR with framework", userCounts, userServiceRRWaitTimes);
		templateChart.addSeries("MT without framework", userCounts, userMTWaitTimes);
		templateChart.addSeries("MT with framework", userCounts, userServiceMTWaitTimes);
		//templateChart.addSeries("PF without framework", userCounts, userPFWaitTimes);
		//templateChart.addSeries("PF with framework", userCounts, userServicePFWaitTimes);
		
		return templateChart;
	}
	
	private double[] getSystemChannelWaitTimes(System[] systems) {
		double[] result = new double[systems.length];
		
		for (int i = 0; i < result.length; i++) {
			System system = systems[i];
			
			result[i] = system.getWaitTime();
		}

		return result;
	}	
}
