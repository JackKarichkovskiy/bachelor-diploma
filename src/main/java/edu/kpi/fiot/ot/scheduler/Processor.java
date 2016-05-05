package edu.kpi.fiot.ot.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;

public abstract class Processor {
	
	protected int coreNumber;
	
	protected Queue queue;
	
	protected Core[] cores;
	
	public List<Packet> donePackets = new ArrayList<>();
	
	public List<Packet> obsoletePackets = new ArrayList<>();
	
	public Processor(int coreNumber, Queue queue){
		this.coreNumber = coreNumber;
		this.queue = queue;
		this.cores = new Core[this.coreNumber];
		for(int i = 0; i < this.cores.length; i++){
			this.cores[i] = new Core(i);
		}
	}
	
	public long firstCalcEnd(){
		long min = Long.MAX_VALUE;
		for (Core core : cores) {
			if (!core.isEmpty()) {
				Packet task = core.getCurrentPacket();
				long lastCalcTime = task.getLastCalcTime();
				long calcLeft = task.getCalcLeft();
				min = Math.min(min, lastCalcTime + calcLeft);
			}
		}

		return min;
	}
	
	public abstract void solvePackets();
	
	public boolean tryToAddNewPacket(Packet task) {
		for (Core core : cores) {
			if (core.isEmpty()) {
				core.setCurrentPacket(task);
				long waitTime = Scheduler.currentTime() - task.getEntryTime();
				task.setWaitTime(waitTime);
				return true;
			}
		}

		return false;
	}
	
	public Set<User> getUsersInProcessor(){
		Set<User> users = new HashSet<>();
		for(Core core : cores){
			Packet packet = core.getCurrentPacket();
			if(packet != null){
				users.add(packet.getUser());
			}
		}
		return users;
	}
	
	public Set<Service> getServicesInProcessor(){
		Set<Service> services = new HashSet<>();
		for(Core core : cores){
			Packet packet = core.getCurrentPacket();
			if(packet != null){
				services.add(packet.getService());
			}
		}
		return services;
	}
}
