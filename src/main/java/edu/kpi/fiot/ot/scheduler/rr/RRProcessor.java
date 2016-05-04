package edu.kpi.fiot.ot.scheduler.rr;

import edu.kpi.fiot.ot.scheduler.Core;
import edu.kpi.fiot.ot.scheduler.Packet;
import edu.kpi.fiot.ot.scheduler.Processor;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;

public class RRProcessor extends Processor {

	private final int tau;

	private Core nextSolveCore;

	public RRProcessor(int coreNumber, Queue queue, int tau) {
		super(coreNumber, queue);
		this.tau = tau;
	}

	@Override
	public long firstCalcEnd() {
		nextSolveCore = null;
		long min = Long.MAX_VALUE;
		for (Core core : cores) {
			if (!core.isEmpty()) {
				Packet packet = core.getCurrentPacket();
				long lastCalcTime = packet.getLastCalcTime();
				min = Math.min(min, lastCalcTime + packet.getCalcLeft());
			}
		}

		return min;
	}

	public void solvePackets() {
		long currentTime = Scheduler.currentTime();
		//long newTime = currentTime + tau;
		for (Core core : cores) {
			if (!core.isEmpty()) {
				Packet packet = core.getCurrentPacket();
				long deadline = packet.getDeadline();
				long calcLeft = packet.getCalcLeft();
				long lastCalcTime = packet.getLastCalcTime();
				
				// DEADLINE PROCESSING
//				boolean isDeadline = calcLeft < tau ? lastCalcTime + calcLeft > deadline
//						: lastCalcTime + tau > deadline;
//				if (isDeadline) {
//					obsoletePackets.add(packet);
//					System.out.println("[INFO]-" + currentTime + ": Packet " + packet.getId() + "(" + packet.getCalcLeft()
//							+ ") is going to OBSOLETED packets");
//					core.setCurrentPacket(packet = queue.getNextPacket());
//				}
				if (packet != null) {
					packet.subCalcLeft(calcLeft = packet.getCalcLeft());
					long newTime = currentTime + calcLeft;
					packet.setLastCalcTime(newTime);
					if (packet.getCalcLeft() == 0) {
						donePackets.add(packet);
						System.out.println("[INFO]-" + newTime + ": Packet " + packet.getId() + "("
								+ packet.getCalcLeft() + ") is going to DONE packets");
						core.setCurrentPacket(packet = queue.getNextPacket());
					}
					Scheduler.setCurrentTime(newTime);
				}
			}
		}

		// ROLLING TASKS IN CORES
//		Packet prevPacket = cores[cores.length - 1].getCurrentPacket();
//		for(int i = 0; i < cores.length; i++){
//			Core core = cores[i];
//			Packet packet = core.getCurrentPacket();
//			core.setCurrentPacket(prevPacket);
//			prevPacket = packet;
//		}
	}

	

}
