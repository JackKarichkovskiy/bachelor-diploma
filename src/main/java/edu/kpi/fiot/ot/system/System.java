package edu.kpi.fiot.ot.system;

import java.util.List;

import edu.kpi.fiot.ot.scheduler.Scheduler;

public class System {
	
	private List<User> users;
	
	private Scheduler scheduler;

	public void run(){
		scheduler.go();
	}
	
	public double getChannelCapacity(){
		return scheduler.getChannelCapacity();
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
