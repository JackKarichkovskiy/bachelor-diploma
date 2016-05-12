package edu.kpi.fiot.ot.scheduler.preprocessor;

import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.System;
import edu.kpi.fiot.ot.system.User;

public class UserPreProcessor implements PreProcessor{

	private System system;
	
	private Queue queue;
	
	private List<User> users;

	private Random ran = new Random(500);
	
	public UserPreProcessor() {
	}
	
	@Override
	public Packet getNextPacket() {
		//Collections.shuffle(users, ran);
		
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

	public void setSystem(System system) {
		this.system = checkNotNull(system);
		this.queue = system.getScheduler().getQueue();
		this.users = system.getUsers();
	}

}
