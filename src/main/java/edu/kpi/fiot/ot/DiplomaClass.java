package edu.kpi.fiot.ot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.chartpart.Chart;

import edu.kpi.fiot.ot.configuration.AppConfiguration;
import edu.kpi.fiot.ot.test.ChannelCapacityChart;
import edu.kpi.fiot.ot.test.PacketWaitTimeChart;
import edu.kpi.fiot.ot.test.SimulationClass;
import edu.kpi.fiot.ot.test.SystemFairnessIndexChart;

public class DiplomaClass {

	private final static String OUTPUT_FILE = "out/log.txt";
	
	public static void main(String[] args) throws FileNotFoundException {
		PrintStream console = System.out;
		try (PrintStream fw = new PrintStream(new File(OUTPUT_FILE))) {
			System.setOut(console);

			SimulationClass simula = new SimulationClass();
			
			int startUserCount = Integer.parseInt(AppConfiguration.getInstance().getProperty("min_users"));
			int endUserCount = Integer.parseInt(AppConfiguration.getInstance().getProperty("max_users"));
			int userPointsCount = Integer.parseInt(AppConfiguration.getInstance().getProperty("user_points_count"));
			simula.runSimulation(startUserCount, endUserCount, userPointsCount);
			Chart channelCapacityChart = new ChannelCapacityChart().getChart(simula);
			new SwingWrapper<>(channelCapacityChart).displayChart();
			Chart packetWaitTimeChart = new PacketWaitTimeChart().getChart(simula);
			new SwingWrapper<>(packetWaitTimeChart).displayChart();
			Chart systemFairnessChart = new SystemFairnessIndexChart().getChart(simula);
			new SwingWrapper<>(systemFairnessChart).displayChart();
		} finally {
			System.setOut(console);
		}
	}
}
