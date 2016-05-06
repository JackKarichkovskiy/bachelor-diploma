package edu.kpi.fiot.ot.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Scheduler;

public class System {
	
	private List<User> users;
	
	private Scheduler scheduler;

	public void run(){
		scheduler.go();
	}
	
	public Set<User> getUsersInSystem(){
		Set<User> users = new HashSet<>();
		Set<User> usersInQueue = scheduler.getQueue().getUsersInQueue();
		Set<User> usersInProc = scheduler.getProc().getUsersInProcessor();
		users.addAll(usersInQueue);
		users.addAll(usersInProc);
		return users;
	}
	
	public Set<Service> getServicesInSystem(){
		Set<Service> services = new HashSet<>();
		Set<Service> servicesInQueue = scheduler.getQueue().getServicesInQueue();
		Set<Service> servicesInProc = scheduler.getProc().getServicesInProcessor();
		services.addAll(servicesInQueue);
		services.addAll(servicesInProc);
		return services;
	}
	
	public double getChannelCapacity(){
		return scheduler.getChannelCapacity();
	}
	
	public double getWaitTime(){
		return scheduler.getWaitTime();
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
