package edu.kpi.fiot.ot.test;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import edu.kpi.fiot.ot.system.System;

public class ChannelCapacityChart extends AbstractTestChart {
	
	@Override
	public Chart getChart(SimulationClass simula) {
		XYChart templateChart = getTemplateChart("Channel Capacity Chart", "User count", "Channel Capacity");
		
		double[] userCounts = simula.getUserCounts();
		double[] userRRCapacities = getSystemChannelCapacities(simula.getRrsWithoutFramework());
		double[] userServiceRRCapacities = getSystemChannelCapacities(simula.getRrsWithFramework());
		double[] userMTCapacities = getSystemChannelCapacities(simula.getMtsWithoutFramework());
		double[] userServiceMTCapacities = getSystemChannelCapacities(simula.getMtsWithFramework());
		//double[] userPFCapacities = getSystemChannelCapacities(simula.getPfsWithoutFramework());
		//double[] userServicePFCapacities = getSystemChannelCapacities(simula.getPfsWithFramework());
		
		templateChart.addSeries("RR without framework", userCounts, userRRCapacities);
		templateChart.addSeries("RR with framework", userCounts, userServiceRRCapacities);
		templateChart.addSeries("MT without framework", userCounts, userMTCapacities);
		templateChart.addSeries("MT with framework", userCounts, userServiceMTCapacities);
		//templateChart.addSeries("PF without framework", userCounts, userPFCapacities);
		//templateChart.addSeries("PF with framework", userCounts, userServicePFCapacities);
		
		return templateChart;
	}
	
	private double[] getSystemChannelCapacities(System[] systems) {
		double[] result = new double[systems.length];
		
		for (int i = 0; i < result.length; i++) {
			System system = systems[i];
			
			result[i] = system.getChannelCapacity();
		}

		return result;
	}	
	
/*	public static void main(String[] args) {
		double[] x = { 0, 1, 2 };
		double[] y1 = { 0, 1, 2 };
		double[] y2 = { 2, 1, 0 };

		// Create Chart
		XYChart chart = new XYChartBuilder().width(800).height(600).title("Test Chart").xAxisTitle("X").yAxisTitle("Y")
				.build();

		// Customize Chart
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
		chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
		// chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
		// chart.getStyler().setPlotMargin(0);
		// chart.getStyler().setPlotContentSize(.95);

		chart.addSeries("y1(x)", x, y1);
		chart.addSeries("y2(x)", x, y2);

		new SwingWrapper<>(chart).displayChart();
	}
*/
}
