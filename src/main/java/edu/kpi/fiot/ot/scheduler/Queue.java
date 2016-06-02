package edu.kpi.fiot.ot.scheduler;

import java.util.Set;

import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;
import edu.kpi.fiot.ot.test.SimulationClass;

/**
 * Abstract class that represents the queue of tasks or packets in scheduling algorithm.
 */
public abstract class Queue {

	/**
	 * The statistic time of being in some state.
	 */
	protected long queueSizeTime = 0;
	
	protected static final int QUEUE_SIZE_LIMIT = (int) (2.5 * SimulationClass.CORE_NUMBER);

	/**
	 * Returns the packet from the head of queue.
	 * 
	 * @return packet from the head of queue.
	 */
	public abstract Packet getNextPacket();

	/**
	 * Adds the packet to the queue.
	 * 
	 * @param packet - packet that needs to be added to the queue.
	 */
	public abstract void addPacket(Packet packet);

	public long getQueueSizeTime() {
		return queueSizeTime;
	}

	public void setQueueSizeTime(long queueSizeTime) {
		this.queueSizeTime = queueSizeTime;
	}
	
	/**
	 * Returns the set of users which packets have already been in the queue.
	 * 
	 * @return the set of users which packets have already been in the queue.
	 */
	public abstract Set<User> getUsersInQueue();
	
	/**
	 * Returns the set of services which packets have already been in the queue.
	 * 
	 * @return the set of services which packets have already been in the queue.
	 */
	public abstract Set<Service> getServicesInQueue();
}
