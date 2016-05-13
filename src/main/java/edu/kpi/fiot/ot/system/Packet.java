package edu.kpi.fiot.ot.system;

/**
 * Class that represents the packet in the system.
 */
public class Packet {
	
	/**
	 * Time when packet was created and comes to logical channel buffer.
	 */
	private long creatingTime;

	/**
	 * Time when packet came to the scheduler queue.
	 */
	private long entryTime;

	/**
	 * Time when packet is considered as obsoleted.
	 */
	private long deadline;
	
	/**
	 * Time that lefts to successfully finish processing of it.
	 */
	private long calcLeft;

	/**
	 * Time when packet was processed last time. 
	 */
	private long lastCalcTime;

	/**
	 * The time of waiting in queues between processing.
	 */
	private long waitTime;
	
	/**
	 * User that creates packet.
	 */
	private User user;
	
	/**
	 * Service that creates packet.
	 */
	private Service service;

	/**
	 * ID of a packet.
	 */
	private final int id;

	/**
	 * Auto incremented number.
	 */
	private static int gen_id = 1;
	
	public Packet() {
		this.id = gen_id++;
	}
	
	public Packet(long creatingTime, long calcTime, long deadline) {
		this();
		this.creatingTime = creatingTime;
		this.deadline = this.creatingTime + deadline;
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
	
	@Override
	public String toString() {
		return "Packet " + getId() + "-U" + getUser().getId() + "-S" + getService().getId() + "(" + getCalcLeft() +")";
	}
}
