package edu.kpi.fiot.ot.scheduler;

import java.util.HashMap;
import java.util.Map;

import edu.kpi.fiot.ot.scheduler.preprocessor.PreProcessor;
import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.User;

/**
 * Class that coordinates and dispatches the actions of scheduler parts.
 * Also monitors and look after the virtual time.
 */
public class Scheduler {

	/**
	 * Queue or buffer of scheduling algorithm.
	 */
	private Queue queue;

	/**
	 * Processor or resource of scheduling algorithm.
	 */
	private Processor proc;

	/**
	 * Preprocessor of MAC-layer scheduler.
	 */
	public static PreProcessor preProc;

	/**
	 * Limit of time after which the scheduling process ends.
	 */
	private long timeLimit;

	/**
	 * Number that represents current virtual time in the system.
	 */
	private static long currentTime = 0;

	public Scheduler(long timeLimit, Processor proc, PreProcessor preProc) {
		this.timeLimit = timeLimit;
		this.proc = proc;
		this.queue = this.proc.queue;
		this.preProc = preProc;
	}

	/**
	 * Method that runs the scheduling process.
	 */
	public void go() {
		currentTime = 0;

		while (currentTime < timeLimit) {
			long nextEntry = preProc.getNextEntryTime();
			long nextCalc = proc.firstCalcEnd();
			if (nextEntry < nextCalc) {
				currentTime = nextEntry;
				Packet newPacket = preProc.getNextPacket();
				if (!proc.tryToAddNewPacket(newPacket))
					queue.addPacket(newPacket);
			} else {
				proc.solvePackets();
			}
		}
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public Processor getProc() {
		return proc;
	}

	public void setProc(Processor proc) {
		this.proc = proc;
	}

	/**
	 * Returns the channel capacity of the system in packets per virtual unit of time.
	 * 
	 * @return the channel capacity of the system in packets per virtual unit of time.
	 */
	public double getChannelCapacity() {
		double donePacketsCount = (double) proc.donePackets.size();
		//long currentTime = currentTime();
		long currentTime = timeLimit;
		return donePacketsCount / currentTime;
	}

	/**
	 * Returns the average wait time of packets in system.
	 * 
	 * @return the average wait time of packets in system.
	 */
	public double getAverageWaitTime() {
		double sum = .0;
		for (Packet task : proc.obsoletePackets) {
			sum += task.getWaitTime();
		}
		for (Packet task : proc.donePackets) {
			sum += task.getWaitTime();
		}
		return sum / (proc.obsoletePackets.size() + proc.donePackets.size());
	}

	/**
	 * Returns the total wait time of packets in system.
	 * @return the total wait time of packets in system.
	 */
	public long getWaitTime() {
		long sum = 0;
		for (Packet task : proc.obsoletePackets) {
			sum += task.getWaitTime();
		}
		for (Packet task : proc.donePackets) {
			sum += task.getWaitTime();
		}
		return sum;
	}

	/**
	 * Returns average waiting percent of cores in the system.
	 * 
	 * @return average waiting percent of cores in the system.
	 */
	public double getAverageCoreWaiting() {
		double sum = .0;
		for (Core core : proc.cores) {
			sum += core.getWaitTime();
		}
		return sum / (proc.cores.length * timeLimit);
	}

	/**
	 * Returns the map where the key is a user in system and
	 * the value is the count of packets that have done.
	 * 
	 * @return the map with user and its done packets.
	 */
	public Map<User, Integer> getUserDonePackets(){
		Map<User, Integer> userDonePackets = new HashMap<>();
		for(Packet packet : proc.donePackets){
			User user = packet.getUser();
			if(!userDonePackets.containsKey(user)){
				userDonePackets.put(user, 1);
			}else{
				int packetCount = userDonePackets.get(user);
				userDonePackets.put(user, ++packetCount);
			}
		}
		return userDonePackets;
	}
	
	public double getObsoletePercent() {
		return (double) proc.obsoletePackets.size() / (proc.obsoletePackets.size() + proc.donePackets.size());
	}

	public double getAverageQueueSize() {
		double result = (double) queue.getQueueSizeTime() / Scheduler.currentTime();
		return result;
	}

	public static long currentTime() {
		return currentTime;
	}

	public static void setCurrentTime(long newTime) {
		if (newTime >= currentTime) {
			currentTime = newTime;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
