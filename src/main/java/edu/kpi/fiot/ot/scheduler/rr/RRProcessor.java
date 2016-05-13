package edu.kpi.fiot.ot.scheduler.rr;

import edu.kpi.fiot.ot.scheduler.Core;
import edu.kpi.fiot.ot.scheduler.Processor;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Packet;

/**
 * Class that implements processor in the round-robin scheduling.
 */
public class RRProcessor extends Processor {

	/**
	 * Field that stores current core number that will be processed in the next time.
	 */
	private int currentCoreNum = 0;

	public RRProcessor(int coreNumber, Queue queue) {
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
	
	@Override
	public boolean tryToAddNewPacket(Packet packet) {
		for(int j = 0; j < cores.length; 
				currentCoreNum = (currentCoreNum + 1) % cores.length, j++){
			Core core;
			if((core = cores[currentCoreNum]).isEmpty()){
				core.setCurrentPacket(packet);
				long waitTime = Scheduler.currentTime() - packet.getCreatingTime();
				packet.setWaitTime(waitTime);
				return true;
			}
		}
		
		return false;
	}
}
