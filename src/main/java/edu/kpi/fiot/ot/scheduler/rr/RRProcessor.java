package edu.kpi.fiot.ot.scheduler.rr;

import edu.kpi.fiot.ot.scheduler.Core;
import edu.kpi.fiot.ot.scheduler.Packet;
import edu.kpi.fiot.ot.scheduler.Processor;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;

public class RRProcessor extends Processor {

	private Core nextSolveCore;

	public RRProcessor(int coreNumber, Queue queue) {
		super(coreNumber, queue);
	}

	/*
	 * @Override public long firstCalcEnd() { nextSolveCore = null; long min =
	 * Long.MAX_VALUE; for (Core core : cores) { if (!core.isEmpty()) { Packet
	 * packet = core.getCurrentPacket(); long lastCalcTime =
	 * packet.getLastCalcTime(); min = Math.min(min, lastCalcTime +
	 * packet.getCalcLeft()); } }
	 * 
	 * return min; }
	 */

	public void solvePackets() {
		long currentTime = Scheduler.currentTime();
		long min = Long.MAX_VALUE;
		Core solveCore = null;
		for (Core core : cores) {
			if (!core.isEmpty()) {
				Packet packet = core.getCurrentPacket();
				long lastCalcTime = packet.getLastCalcTime();
				long calcLeft = packet.getCalcLeft();
				if (min > lastCalcTime + calcLeft) {
					solveCore = core;
					min = lastCalcTime + calcLeft;
				}
			}
		}
		if (solveCore != null) {
			Packet packet = solveCore.getCurrentPacket();
			long newTime = packet.getLastCalcTime() + packet.getCalcLeft();
			Scheduler.setCurrentTime(newTime);
			donePackets.add(packet);
			System.out.println("[INFO]-" + newTime + ": Packet " + packet.getId() + "(" + packet.getCalcLeft()
					+ ") is going to DONE packets");
			Packet nextPacket = queue.getNextPacket();
			solveCore.setCurrentPacket(nextPacket);
		}
	}

}
