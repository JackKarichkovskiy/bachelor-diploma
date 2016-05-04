package edu.kpi.fiot.ot.scheduler;

public class Core {
	private Packet currentPacket;

	private int coreNum;

	private long waitTime;

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
			System.out.println("[INFO]-" + Scheduler.currentTime() + ": Packet " + currentPacket.getId() + "("
					+ currentPacket.getCalcLeft() + ") going to core " + coreNum);
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
