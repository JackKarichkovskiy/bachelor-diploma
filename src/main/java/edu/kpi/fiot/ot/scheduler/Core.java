package edu.kpi.fiot.ot.scheduler;

import edu.kpi.fiot.ot.system.Packet;

/**
 * Class that represents a core of processor that could process only one incoming task at the moment.
 */
public class Core {
	
	/**
	 * Packet that processing in the core.
	 */
	private Packet currentPacket;

	/**
	 * Number of core in processor.
	 */
	private int coreNum;

	/**
	 * Time while a core was empty during the simulation was going.
	 */
	private long waitTime;

	/**
	 * Time while the last packet leaves a core.
	 */
	private long lastPacketOutTime = 0;

	public Core(int coreNum) {
		this.coreNum = coreNum;
	}

	public Packet getCurrentPacket() {
		return currentPacket;
	}

	public void setCurrentPacket(Packet currentPacket) {
		if (currentPacket != null) {
			if (isEmpty()) {
				waitTime += Scheduler.currentTime() - lastPacketOutTime;
			}
			currentPacket.setLastCalcTime(Scheduler.currentTime());
			System.out.println("[INFO]-" + Scheduler.currentTime() + ": "+ currentPacket +" going to core " + coreNum);
		} else {
			lastPacketOutTime = Scheduler.currentTime();
		}
		this.currentPacket = currentPacket;
	}

	public int getCoreNum() {
		return coreNum;
	}

	public boolean isEmpty() {
		return currentPacket == null;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
}
