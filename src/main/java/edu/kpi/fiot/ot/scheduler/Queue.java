package edu.kpi.fiot.ot.scheduler;

import java.util.Set;

import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;

public abstract class Queue {

	protected long queueSizeTime = 0;

	public abstract Packet getNextPacket();

	public abstract void addPacket(Packet task);

	public long getQueueSizeTime() {
		return queueSizeTime;
	}

	public void setQueueSizeTime(long queueSizeTime) {
		this.queueSizeTime = queueSizeTime;
	}
	
	public abstract Set<User> getUsersInQueue();
	
	public abstract Set<Service> getServicesInQueue();
}
