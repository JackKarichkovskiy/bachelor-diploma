package edu.kpi.fiot.ot.system;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents user of the system.
 */
public class User {

	/**
	 * ID of user.
	 */
	private final int id;

	/**
	 * Auto incremented number.
	 */
	private static int gen_id = 1;
	
	/**
	 * List of services that have user.
	 */
	private List<Service> services = new ArrayList<>();
	
	/**
	 * Time that needs user to transfer packets to the access point.
	 */
	private int averageTransferTime;

	public User() {
		this.id = gen_id++;
	}
	
	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
		for(Service service : services){
			service.setUser(this);
		}
	}
	
	/**
	 * Method that looks for the earliest coming packet from his services.
	 * 
	 * @return time when comes first packet from services.
	 */
	public long getNextEntryPacketTime(){
		long min = Long.MAX_VALUE;
		for(Service service : services){
			if(min > service.getNextPacketEntryTime()){
				min = service.getNextPacketEntryTime();
			}
		}
		return min;
	}
	
	/**
	 * Method that looks for the earliest coming packet from his services.
	 * 
	 * @return packet that comes first from services.
	 */
	public Packet getNextPacket(){
		Service minService = null;
		long min = Long.MAX_VALUE;
		for(Service service : services){
			if(min >= service.getNextPacketEntryTime()){
				min = service.getNextPacketEntryTime();
				minService = service;
			}
		}
		
		if(minService != null){
			Packet packet = minService.pollPacket();
			packet.setUser(this);
			long calcLeft = packet.getCalcLeft();
			packet.setCalcLeft(calcLeft + this.averageTransferTime);
			return packet;
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public int getAverageTransferTime() {
		return averageTransferTime;
	}

	public void setAverageTransferTime(int averageTransferTime) {
		this.averageTransferTime = averageTransferTime;
	}
}
