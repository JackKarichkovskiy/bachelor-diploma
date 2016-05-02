package edu.kpi.fiot.ot.scheduler;

import java.util.Random;

public class Packet {
	private final long entryTime;

	private long calcLeft;

	private long lastCalcTime;

	private long waitTime;

	private final int id;

	private static int gen_id = 1;

	private static Random ran = new Random(201);
	
	public Packet(long entryTime, long calcTime) {
		super();
		this.entryTime = Math.abs(entryTime);
		this.calcLeft = calcTime;
		//this.lastCalcTime = this.entryTime;
		this.id = gen_id++;
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
