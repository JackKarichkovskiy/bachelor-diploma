package edu.kpi.fiot.ot;

import java.util.ArrayList;
import java.util.List;

import edu.kpi.fiot.ot.scheduler.Processor;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.scheduler.preprocessor.PreProcessor;
import edu.kpi.fiot.ot.scheduler.preprocessor.UserPreProcessor;
import edu.kpi.fiot.ot.scheduler.rr.RRProcessor;
import edu.kpi.fiot.ot.scheduler.rr.RRQueue;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.System;
import edu.kpi.fiot.ot.system.User;
import edu.kpi.fiot.ot.system.generator.PuassonGenerator;

public class DiplomaClass {

	public static void main(String[] args) {
		int constCoreNum = 2;
		
		// System configuring
		System system = new System();
		List<Service> services = new ArrayList<Service>() {
			{
				add(new Service("Video call", 15, new PuassonGenerator(0.02)));
				add(new Service("Video buffering", 50, new PuassonGenerator(0.006666)));
				add(new Service("Email service", 30, new PuassonGenerator(0.05)));
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
		Scheduler scheduler = new Scheduler(1000, proc, preProc);
		system.setScheduler(scheduler);
		preProc.setSystem(system);

		system.run();
	}

}
