package edu.kpi.fiot.ot;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.internal.chartpart.Chart;

import edu.kpi.fiot.ot.test.ChannelCapacityChart;
import edu.kpi.fiot.ot.test.PacketWaitTimeChart;
import edu.kpi.fiot.ot.test.SimulationClass;

public class DiplomaClass {

	public static void main(String[] args) {
		SimulationClass simula = new SimulationClass();
		simula.runSimulation(1, 100, 20);
		Chart channelCapacityChart = new ChannelCapacityChart().getChart(simula);
		new SwingWrapper<>(channelCapacityChart).displayChart();
		Chart packetWaitTimeChart = new PacketWaitTimeChart().getChart(simula);
		new SwingWrapper<>(packetWaitTimeChart).displayChart();
	}

	/*private static void runTest() {
		int constCoreNum = 1;

		// System configuring
		System system = new System();
		List<Service> services = new ArrayList<Service>() {
			{
				add(new Service("Video call", 500, new PuassonGenerator(0.002)));
				add(new Service("Video buffering", 1000, new PuassonGenerator(0.001)));
				// add(new Service("Email service", 30, new
				// PuassonGenerator(0.05)));
			}
		};
		User user = new User();
		user.setServices(services);
		List<User> users = new ArrayList<>();
		users.add(user);
		system.setUsers(users);

		// Scheduler configuring
		Queue queue = new RRQueue();
		Processor proc = new RRProcessor(constCoreNum, queue);
		PreProcessor preProc = new UserPreProcessor();
		Scheduler scheduler = new Scheduler(10000, proc, preProc);
		system.setScheduler(scheduler);
		preProc.setSystem(system);

		system.run();

		java.lang.System.out.println(system.getChannelCapacity());
	}*/
}
