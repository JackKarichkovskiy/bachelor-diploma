package edu.kpi.fiot.ot.scheduler;

public class Packet {
	private long entryTime;

	private long deadline;
	
	private long calcLeft;

	private long lastCalcTime;

	private long waitTime;

	private final int id;

	private static int gen_id = 1;
	
	public Packet() {
		this.id = gen_id++;
	}
	
	public Packet(long entryTime, long calcTime, long deadline) {
		this();
		this.entryTime = Math.abs(entryTime);
		this.deadline = this.entryTime + deadline;
		this.calcLeft = calcTime;
		//this.lastCalcTime = this.entryTime;
	}
	
	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public void setCalcLeft(long calcLeft) {
		this.calcLeft = calcLeft;
	}

	public int getId() {
		return id;
	}

	public long getCalcLeft() {
		return calcLeft;
	}
	
	public void subCalcLeft(long time){
		calcLeft -= time;
		if(calcLeft < 0){
			calcLeft = 0;
		}
	}

	public long getEntryTime() {
		return entryTime;
	}
	
	public long getDeadline() {
		return deadline;
	}
	
	public long getLastCalcTime() {
		return lastCalcTime;
	}

	public void setLastCalcTime(long lastCalcTime) {
		this.lastCalcTime = lastCalcTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
}
