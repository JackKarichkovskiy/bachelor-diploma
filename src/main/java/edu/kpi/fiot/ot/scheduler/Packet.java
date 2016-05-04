package edu.kpi.fiot.ot.scheduler;

import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;

public class Packet {
	private long creatingTime;

	private long entryTime;

	private long deadline;
	
	private long calcLeft;

	private long lastCalcTime;

	private long waitTime;
	
	private User user;
	
	private Service service;

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

	public long getCreatingTime() {
		return creatingTime;
	}

	public void setCreatingTime(long creatingTime) {
		this.creatingTime = creatingTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
}
