package edu.kpi.fiot.ot.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.kpi.fiot.ot.configuration.AppConfiguration;
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

/**
 * Class that runs the simulation of different systems with different entry parameters.
 */
public class SimulationClass {

	/**
	 * Number of cores in systems.
	 */
	public static final int CORE_NUMBER = 
			Integer.parseInt(AppConfiguration.getInstance().getProperty("core_number"));

	/**
	 * Time limit of simulation.
	 */
	private static final long TIME_LIMIT = 
			Long.parseLong(AppConfiguration.getInstance().getProperty("time_limit"));
	
	/**
	 * Minimum time of transfer for user.
	 */
	private static final int MIN_USER_TRANSFER_TIME = 
			Integer.parseInt(AppConfiguration.getInstance().getProperty("min_user_transfer_time"));
	
	/**
	 * Maximum time of transfer for user.
	 */
	private static final int MAX_USER_TRANSFER_TIME = 
			Integer.parseInt(AppConfiguration.getInstance().getProperty("max_user_transfer_time"));

	/**
	 * Array of different user count parameters.
	 */
	private double[] userCounts;
	
	/**
	 * Array of rr systems without framework after simulation.
	 */
	private System[] rrsWithoutFramework;

	/**
	 * Array of rr systems with framework after simulation.
	 */
	private System[] rrsWithFramework;

	private System[] pfsWithoutFramework;

	private System[] pfsWithFramework;


	/**
	 * Array of mt systems without framework after simulation.
	 */
	private System[] mtsWithoutFramework;


	/**
	 * Array of mt systems with framework after simulation.
	 */
	private System[] mtsWithFramework;
	
	private Random ran = new Random(300);
	
	/**
	 * Runs simulation.
	 * 
	 * @param start - min user count
	 * @param end - max user count
	 * @param pointCount - count of points that needs to be built
	 */
	public void runSimulation(int start, int end, int pointCount){
		userCounts = constructUserCounts(start, end, pointCount);
		
		rrsWithoutFramework = createRrsWithoutFramework(userCounts);
		rrsWithFramework = createRrsWithFramework(userCounts);
		mtsWithoutFramework = createMtsWithoutFramework(userCounts);
		mtsWithFramework = createMtsWithFramework(userCounts);
	}
	
	/**
	 * Constructs array of user counts.
	 * @param start - min user count
	 * @param end - max user count
	 * @param count - count of points that needs to be built
	 * @return array of point counts
	 */
	private double[] constructUserCounts(int start, int end, int count) {
		double[] result = new double[count];
		int step = (end + 1 - start) / count;
		for (int i = 0, userCount = start; i < result.length; i++, userCount += step) {
			result[i] = userCount;
		}
		return result;
	}

	/**
	 * Constructs list of services.
	 * 
	 * @return list of services.
	 */
	private List<Service> constructServices(){
		List<Service> services =  new ArrayList<Service>() {
			{
				add(new Service("Video buffering1", 30, new UniformGenerator(0.01, 0.0133)));//75-100
				add(new Service("Video buffering1", 30, new UniformGenerator(0.01, 0.0133)));//75-100
				add(new Service("Video buffering1", 100, new UniformGenerator(0.004, 0.00667)));//150-250
				add(new Service("Video buffering1", 100, new UniformGenerator(0.00667, 0.008)));//125-150
				//add(new Service("Video buffering1", 30, new UniformGenerator(0.008, 0.01)));//100-125
				//add(new Service("Weather service", 10, new UniformGenerator(0.0111, 0.02)));//50-90
				//add(new Service("Email service", 100, new UniformGenerator(0.00143, 0.002))); //500-700
			}
		};
		return services;
	}
	
	/**
	 * Constructs user with random transfer time.
	 * 
	 * @return constructed user.
	 */
	private User constructUser() {
		User user = new User();
		int userTransferTime = MIN_USER_TRANSFER_TIME + ran.nextInt(MAX_USER_TRANSFER_TIME - MIN_USER_TRANSFER_TIME);
		user.setAverageTransferTime(userTransferTime);
		user.setServices(constructServices());
		return user;
	}

	/**
	 * Constructs list of users.
	 * 
	 * @param userCount - user count that needs to be constructed
	 * @return list of users
	 */
	private List<User> constructUsers(double userCount) {
		List<User> users = new ArrayList<>((int) userCount);
		for (int j = 0; j < userCount; j++) {
			users.add(constructUser());
		}
		return users;
	}

	/**
	 * Construct system without setting the scheduler object.
	 * 
	 * @param userCount - count of users
	 * @return constructed system
	 */
	private System constructSystemWithoutScheduler(double userCount) {
		System system = new System();
		system.setUsers(constructUsers(userCount));
		return system;
	}

	/**
	 * Runs the simulation of rr systems without framework with different count of users.
	 * 
	 * @param userNums - array of different entry numbers of users
	 * @return simulated systems
	 */
	private System[] createRrsWithoutFramework(double[] userNums) {
		java.lang.System.out.println("--------RR without framework--------");
		System[] result = new System[userNums.length];

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

			result[i] = system;

			java.lang.System.out.println("----------------------------------");
		}
		
		return result;
	}
	
	/**
	 * Runs the simulation of rr systems with framework with different count of users.
	 * 
	 * @param userNums - array of different entry numbers of users
	 * @return simulated systems
	 */
	private System[] createRrsWithFramework(double[] userNums) {
		java.lang.System.out.println("--------RR with framework--------");
		System[] result = new System[userNums.length];

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

			result[i] = system;

			java.lang.System.out.println("----------------------------------");
		}
		
		return result;
	}

	private System[] createPfsWithoutFramework(double[] userNums) {
		java.lang.System.out.println("--------PF without framework--------");
		System[] result = new System[userNums.length];

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

			result[i] = system;

			java.lang.System.out.println("----------------------------------");
		}
		
		return result;
	}
	
	private System[] createPfsWithFramework(double[] userNums) {
		java.lang.System.out.println("--------PF with framework--------");
		System[] result = new System[userNums.length];

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

			result[i] = system;

			java.lang.System.out.println("----------------------------------");
		}
		
		return result;
	}
	
	/**
	 * Runs the simulation of mt systems without framework with different count of users.
	 * 
	 * @param userNums - array of different entry numbers of users
	 * @return simulated systems
	 */
	private System[] createMtsWithoutFramework(double[] userNums) {
		java.lang.System.out.println("--------MT without framework--------");
		System[] result = new System[userNums.length];

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

			result[i] = system;

			java.lang.System.out.println("----------------------------------");
		}
		
		return result;
	}
	
	/**
	 * Runs the simulation of mt systems with framework with different count of users.
	 * 
	 * @param userNums - array of different entry numbers of users
	 * @return simulated systems
	 */
	private System[] createMtsWithFramework(double[] userNums) {
		java.lang.System.out.println("--------MT with framework--------");
		System[] result = new System[userNums.length];

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

			result[i] = system;

			java.lang.System.out.println("----------------------------------");
		}
		
		return result;
	}
	
	public double[] getUserCounts() {
		return userCounts;
	}

	public void setUserCounts(double[] userCounts) {
		this.userCounts = userCounts;
	}

	public System[] getRrsWithoutFramework() {
		return rrsWithoutFramework;
	}

	public void setRrsWithoutFramework(System[] rrsWithoutFramework) {
		this.rrsWithoutFramework = rrsWithoutFramework;
	}

	public System[] getRrsWithFramework() {
		return rrsWithFramework;
	}

	public void setRrsWithFramework(System[] rrsWithFramework) {
		this.rrsWithFramework = rrsWithFramework;
	}

	public System[] getPfsWithoutFramework() {
		return pfsWithoutFramework;
	}

	public void setPfsWithoutFramework(System[] pfsWithoutFramework) {
		this.pfsWithoutFramework = pfsWithoutFramework;
	}

	public System[] getPfsWithFramework() {
		return pfsWithFramework;
	}

	public void setPfsWithFramework(System[] pfsWithFramework) {
		this.pfsWithFramework = pfsWithFramework;
	}

	public System[] getMtsWithoutFramework() {
		return mtsWithoutFramework;
	}

	public void setMtsWithoutFramework(System[] mtsWithoutFramework) {
		this.mtsWithoutFramework = mtsWithoutFramework;
	}

	public System[] getMtsWithFramework() {
		return mtsWithFramework;
	}

	public void setMtsWithFramework(System[] mtsWithFramework) {
		this.mtsWithFramework = mtsWithFramework;
	}
}
