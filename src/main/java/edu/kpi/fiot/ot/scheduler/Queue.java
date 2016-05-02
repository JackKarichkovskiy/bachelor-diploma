package edu.kpi.fiot.ot.scheduler;

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
}
