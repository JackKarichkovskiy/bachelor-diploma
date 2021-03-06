package edu.kpi.fiot.ot.scheduler.mt;

import edu.kpi.fiot.ot.scheduler.Core;
import edu.kpi.fiot.ot.scheduler.Processor;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Packet;

/**
 * Class that implements processor in the maximum-throughput scheduling.
 */
public class MTProcessor extends Processor {

	public MTProcessor(int coreNumber, Queue queue) {
		super(coreNumber, queue);
	}

	@Override
	public void solvePackets() {
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
			System.out.println("[INFO]-" + newTime + ": " + packet + " is going to DONE packets");
			Packet nextPacket = queue.getNextPacket();
			solveCore.setCurrentPacket(nextPacket);
		}
	}
	
}
