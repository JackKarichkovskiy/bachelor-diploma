package edu.kpi.fiot.ot.scheduler.preprocessor;

import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

import java.util.List;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.System;
import edu.kpi.fiot.ot.system.User;

/**
 * Implementation of the preprocessor that works in one dimension (users).
 */
public class UserPreProcessor implements PreProcessor{

	/**
	 * System with users and services.
	 */
	private System system;
	
	/**
	 * List of users in the system.
	 */
	private List<User> users;
	
	public UserPreProcessor() {
	}
	
	@Override
	public Packet getNextPacket() {
		Set<User> systemUsers = system.getUsersInSystem();
		long min = Long.MAX_VALUE;
		User minUser = null;
		for(User user : users){
			if(!systemUsers.contains(user)){
				long nextEntryPacketTime = user.getNextEntryPacketTime();
				if (min >= nextEntryPacketTime) {
					min = nextEntryPacketTime;
					minUser = user;
				}
			}
		}
		
		if(minUser != null){
			return minUser.getNextPacket();
		}
		return null;
	}

	@Override
	public long getNextEntryTime() {
		Set<User> systemUsers = system.getUsersInSystem();
		long min = Long.MAX_VALUE;
		for(User user : users){
			if(!systemUsers.contains(user)){
				min = Math.min(min, user.getNextEntryPacketTime());
			}
		}
		long currentTime = Scheduler.currentTime();
		return min < currentTime ? currentTime : min;
	}

	public System getSystem() {
		return system;
	}

	@Override
	public void setSystem(System system) {
		this.system = checkNotNull(system);
		this.users = system.getUsers();
	}

}
