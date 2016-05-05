package edu.kpi.fiot.ot.test;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.chartpart.Chart;

import edu.kpi.fiot.ot.scheduler.Processor;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.scheduler.mt.MTProcessor;
import edu.kpi.fiot.ot.scheduler.mt.MTQueue;
import edu.kpi.fiot.ot.scheduler.pf.PFProcessor;
import edu.kpi.fiot.ot.scheduler.pf.PFQueue;
import edu.kpi.fiot.ot.scheduler.preprocessor.PreProcessor;
import edu.kpi.fiot.ot.scheduler.preprocessor.UserPreProcessor;
import edu.kpi.fiot.ot.scheduler.preprocessor.UserServicePreProcessor;
import edu.kpi.fiot.ot.scheduler.rr.RRProcessor;
import edu.kpi.fiot.ot.scheduler.rr.RRQueue;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.System;
import edu.kpi.fiot.ot.system.User;
import edu.kpi.fiot.ot.system.generator.UniformGenerator;

public class ChannelCapacityChart extends AbstractTestChart {

	private static final int CORE_NUMBER = 100;
	
	private static final long TIME_LIMIT = 1000;
	
	@Override
	public Chart getChart() {
		XYChart templateChart = getTemplateChart("Channel Capacity Chart", "User count", "Channel Capacity");
		
		double[] userCounts = constructUserCounts(1, 100, 20);
		java.lang.System.out.println("--------RR without framework--------");
		double[] userRRCapacities = getUserRRSchedulerChannelCapacities(userCounts);
		java.lang.System.out.println("--------RR with framework--------");
		double[] userServiceRRCapacities = getUserServiceRRSchedulerChannelCapacities(userCounts);
		java.lang.System.out.println("--------MT without framework--------");
		double[] userMTCapacities = getUserMTSchedulerChannelCapacities(userCounts);
		java.lang.System.out.println("--------MT with framework--------");
		double[] userServiceMTCapacities = getUserServiceMTSchedulerChannelCapacities(userCounts);
		java.lang.System.out.println("--------PF without framework--------");
		double[] userPFCapacities = getUserPFSchedulerChannelCapacities(userCounts);
		java.lang.System.out.println("--------PF with framework--------");
		double[] userServicePFCapacities = getUserServicePFSchedulerChannelCapacities(userCounts);
		
		templateChart.addSeries("RR without framework", userCounts, userRRCapacities);
		templateChart.addSeries("RR with framework", userCounts, userServiceRRCapacities);
		templateChart.addSeries("MT without framework", userCounts, userMTCapacities);
		templateChart.addSeries("MT with framework", userCounts, userServiceMTCapacities);
		templateChart.addSeries("PF without framework", userCounts, userPFCapacities);
		templateChart.addSeries("PF with framework", userCounts, userServicePFCapacities);
		
		return templateChart;
	}
	
	private double[] constructUserCounts(int start, int end, int count){
		double[] result = new double[count];
		int step = (end + 1 - start) / count;
		for(int i = 0, userCount = start; i < result.length; i++, userCount += step){
			result[i] = userCount;
		}
		return result;
	}
	
	private double[] getUserRRSchedulerChannelCapacities(double[] userNums) {
		double[] result = new double[userNums.length];
		
		for (int i = 0; i < result.length; i++) {
			double userCount = userNums[i];
			System system = constructSystemWithoutScheduler(userCount);
			
			// Scheduler configuring
			Queue queue = new RRQueue();
			Processor proc = new RRProcessor(CORE_NUMBER, queue);
			PreProcessor preProc = new UserPreProcessor();
			Scheduler scheduler = new Scheduler(TIME_LIMIT, proc, preProc);
			system.setScheduler(scheduler);
			preProc.setSystem(system);
			
			system.run();
			
			result[i] = system.getChannelCapacity();
			
			java.lang.System.out.println("----------------------------------");
		}

		return result;
	}

	private double[] getUserServiceRRSchedulerChannelCapacities(double[] userNums) {
		double[] result = new double[userNums.length];
		
		for (int i = 0; i < result.length; i++) {
			double userCount = userNums[i];
			System system = constructSystemWithoutScheduler(userCount);
			
			// Scheduler configuring
			Queue queue = new RRQueue();
			Processor proc = new RRProcessor(CORE_NUMBER, queue);
			PreProcessor preProc = new UserServicePreProcessor();
			Scheduler scheduler = new Scheduler(TIME_LIMIT, proc, preProc);
			system.setScheduler(scheduler);
			preProc.setSystem(system);
			
			system.run();
			
			result[i] = system.getChannelCapacity();
			
			java.lang.System.out.println("----------------------------------");
		}

		return result;
	}
	
	private double[] getUserMTSchedulerChannelCapacities(double[] userNums) {
		double[] result = new double[userNums.length];
		
		for (int i = 0; i < result.length; i++) {
			double userCount = userNums[i];
			System system = constructSystemWithoutScheduler(userCount);
			
			// Scheduler configuring
			Queue queue = new MTQueue();
			Processor proc = new MTProcessor(CORE_NUMBER, queue);
			PreProcessor preProc = new UserPreProcessor();
			Scheduler scheduler = new Scheduler(TIME_LIMIT, proc, preProc);
			system.setScheduler(scheduler);
			preProc.setSystem(system);
			
			system.run();
			
			result[i] = system.getChannelCapacity();
			
			java.lang.System.out.println("----------------------------------");
		}

		return result;
	}
	
	private double[] getUserServiceMTSchedulerChannelCapacities(double[] userNums) {
		double[] result = new double[userNums.length];
		
		for (int i = 0; i < result.length; i++) {
			double userCount = userNums[i];
			System system = constructSystemWithoutScheduler(userCount);
			
			// Scheduler configuring
			Queue queue = new MTQueue();
			Processor proc = new MTProcessor(CORE_NUMBER, queue);
			PreProcessor preProc = new UserServicePreProcessor();
			Scheduler scheduler = new Scheduler(TIME_LIMIT, proc, preProc);
			system.setScheduler(scheduler);
			preProc.setSystem(system);
			
			system.run();
			
			result[i] = system.getChannelCapacity();
			
			java.lang.System.out.println("----------------------------------");
		}

		return result;
	}
	
	private double[] getUserPFSchedulerChannelCapacities(double[] userNums) {
		double[] result = new double[userNums.length];
		
		for (int i = 0; i < result.length; i++) {
			double userCount = userNums[i];
			System system = constructSystemWithoutScheduler(userCount);
			
			// Scheduler configuring
			Queue queue = new PFQueue();
			Processor proc = new PFProcessor(CORE_NUMBER, queue);
			PreProcessor preProc = new UserPreProcessor();
			Scheduler scheduler = new Scheduler(TIME_LIMIT, proc, preProc);
			system.setScheduler(scheduler);
			preProc.setSystem(system);
			
			system.run();
			
			result[i] = system.getChannelCapacity();
			
			java.lang.System.out.println("----------------------------------");
		}

		return result;
	}
	
	private double[] getUserServicePFSchedulerChannelCapacities(double[] userNums) {
		double[] result = new double[userNums.length];
		
		for (int i = 0; i < result.length; i++) {
			double userCount = userNums[i];
			System system = constructSystemWithoutScheduler(userCount);
			
			// Scheduler configuring
			Queue queue = new PFQueue();
			Processor proc = new PFProcessor(CORE_NUMBER, queue);
			PreProcessor preProc = new UserServicePreProcessor();
			Scheduler scheduler = new Scheduler(TIME_LIMIT, proc, preProc);
			system.setScheduler(scheduler);
			preProc.setSystem(system);
			
			system.run();
			
			result[i] = system.getChannelCapacity();
			
			java.lang.System.out.println("----------------------------------");
		}

		return result;
	}
	
	private User constructUser(){
		User user = new User();
		user.setServices(new ArrayList<Service>() {
			{
				add(new Service("Video call", 15, new UniformGenerator(0.025, 0.0667)));
				add(new Service("Video buffering", 50, new UniformGenerator(0.008, 0.013)));
				add(new Service("Email service", 30, new UniformGenerator(0.0143, 0.0222)));
			}
		});
		return user;
	}
	
	private List<User> constructUsers(double userCount){
		List<User> users = new ArrayList<>((int)userCount);
		for(int j = 0; j < userCount; j++){
			users.add(constructUser());
		}
		return users;
	}

	private System constructSystemWithoutScheduler(double userCount){
		System system = new System();
		system.setUsers(constructUsers(userCount));
		return system;
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
