package edu.kpi.fiot.ot.scheduler.preprocessor;

import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

import java.util.List;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Packet;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.System;
import edu.kpi.fiot.ot.system.User;

public class UserPreProcessor implements PreProcessor{

	private System system;
	
	private Queue queue;
	
	private List<User> users;
	
	public UserPreProcessor(System system) {
		this.system = checkNotNull(system);
		this.queue = system.getScheduler().getQueue();
		this.users = system.getUsers();
	}
	
	@Override
	public Packet getNextPacket() {
		Set<User> queueUsers = queue.getUsersInQueue();
		long min = Long.MAX_VALUE;
		Service minService = null;
		for(User user : users){
			if(!queueUsers.contains(user)){
				List<Service> services = user.getServices();
				for (Service service : services) {
					if (min >= service.getNextPacketEntryTime()) {
						min = service.getNextPacketEntryTime();
						minService = service;
					}
				}
			}
		}
		
		if(minService != null){
			Packet packet = minService.pollPacket();
			packet.setUser(minService.getUser());
			return packet;
		}
		return null;
	}

	@Override
	public long getNextEntryTime() {
		long min = Long.MAX_VALUE;
		for(User user : users){
			min = Math.min(min, user.getNextEntryPacketTime());
		}
		long currentTime = Scheduler.currentTime();
		return min < currentTime ? currentTime : min;
	}

}
