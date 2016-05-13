package edu.kpi.fiot.ot.system;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Scheduler;

/**
 * Class that represents the whole system that contains of users and its services 
 * and the scheduler that processes packets that incoming from users.
 */
public class System {
	
	/**
	 * List of active users in system.
	 */
	private List<User> users;
	
	/**
	 * Scheduler of MAC-layer.
	 */
	private Scheduler scheduler;

	/**
	 * Method that runs the simulation.
	 */
	public void run(){
		scheduler.go();
	}
	
	/**
	 * Returns set of users which packets are located in queue or processor.
	 * 
	 * @return set of users which packets are located in queue or processor.
	 */
	public Set<User> getUsersInSystem(){
		Set<User> users = new HashSet<>();
		Set<User> usersInQueue = scheduler.getQueue().getUsersInQueue();
		Set<User> usersInProc = scheduler.getProc().getUsersInProcessor();
		users.addAll(usersInQueue);
		users.addAll(usersInProc);
		return users;
	}
	
	/**
	 * Returns set of services which packets are located in queue or processor.
	 * 
	 * @return set of services which packets are located in queue or processor.
	 */
	public Set<Service> getServicesInSystem(){
		Set<Service> services = new HashSet<>();
		Set<Service> servicesInQueue = scheduler.getQueue().getServicesInQueue();
		Set<Service> servicesInProc = scheduler.getProc().getServicesInProcessor();
		services.addAll(servicesInQueue);
		services.addAll(servicesInProc);
		return services;
	}
	
	/**
	 * Returns calculated channel capacity of system after simulation.
	 * 
	 * @return calculated channel capacity of system.
	 */
	public double getChannelCapacity(){
		return scheduler.getChannelCapacity();
	}
	
	/**
	 * Returns total wait time of packets after simulation.
	 * 
	 * @return total wait time of packets.
	 */
	public double getWaitTime(){
		return scheduler.getWaitTime();
	}
	
	/**
	 * Returns fairness index of system calculated by Jain's formula.
	 * @return fairness index of system.
	 */
	public double getFairnessIndex(){
		Map<User, Integer> userDonePackets = scheduler.getUserDonePackets();
		
		long sum1 = 0;
		long sum2 = 0;
		for(User user : users){
			if(userDonePackets.containsKey(user)){
				int packetCount = userDonePackets.get(user);
				sum1 += packetCount;
				sum2 += packetCount * packetCount;
			}
		}
		double result = (double)(sum1 * sum1) / (users.size() * sum2);
		return result;
	}
	
	public double getAverageWaitTime(){
		return scheduler.getAverageWaitTime();
	}
	
	public int getUserCount(){
		return users.size();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}
