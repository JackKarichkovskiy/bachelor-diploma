package edu.kpi.fiot.ot.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;

/**
 * Abstract processor or resource in scheduler system.
 */
public abstract class Processor {
	
	/**
	 * Number of cores or resources in the processor.
	 */
	protected int coreNumber;
	
	/**
	 * Queue where tasks or packets are hold.
	 */
	protected Queue queue;
	
	/**
	 * Cores or resources of processor.
	 */
	protected Core[] cores;
	
	/**
	 * List of packets that have already done.
	 */
	public List<Packet> donePackets = new ArrayList<>();

	/**
	 * List of packets that have already obsoleted.
	 */
	public List<Packet> obsoletePackets = new ArrayList<>();
	
	public Processor(int coreNumber, Queue queue){
		this.coreNumber = coreNumber;
		this.queue = queue;
		this.cores = new Core[this.coreNumber];
		for(int i = 0; i < this.cores.length; i++){
			this.cores[i] = new Core(i);
		}
	}
	
	/**
	 * Default implementation of finding the time when the earliest packet leaves the core.
	 * 
	 * @return time of the earliest packet leaving.
	 */
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
	
	/**
	 * Method that solve the packet or a group of packets in the cores.
	 */
	public abstract void solvePackets();
	
	/**
	 * Method tries to add a packet to some core.
	 * 
	 * @param task - packet that should to be added.
	 * @return true - if adding was successful, otherwise - false.
	 */
	public boolean tryToAddNewPacket(Packet task) {
		for (Core core : cores) {
			if (core.isEmpty()) {
				core.setCurrentPacket(task);
				long waitTime = Scheduler.currentTime() - task.getCreatingTime();
				task.setWaitTime(waitTime);
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Returns the set of users which packets have already been on processor.
	 * 
	 * @return set of users which packets have already been on processor.
	 */
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
	
	/**
	 * Returns the set of services which packets have already been on processor.
	 * 
	 * @return set of services which packets have already been on processor.
	 */
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
