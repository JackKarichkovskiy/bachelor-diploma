package edu.kpi.fiot.ot.test;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.LegendPosition;

public abstract class AbstractTestChart implements TestChart{

	
	protected XYChart getTemplateChart(String title, String xAxisTitle, String yAxisTitle){
		// Create Chart
	    XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build();
	 
	    // Customize Chart
	    chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
	    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
	    chart.getStyler().setYAxisLabelAlignment(Styler.TextAlignment.Right);
	    //chart.getStyler().setYAxisDecimalPattern("$ #,###.##");
	    //chart.getStyler().setPlotMargin(0);
	    //chart.getStyler().setPlotContentSize(.95);
	    
	    return chart;
	}
}
