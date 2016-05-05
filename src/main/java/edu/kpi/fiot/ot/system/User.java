package edu.kpi.fiot.ot.system;

import java.util.ArrayList;
import java.util.List;

import edu.kpi.fiot.ot.scheduler.Packet;

public class User {

	private final int id;

	private static int gen_id = 1;
	
	private List<Service> services = new ArrayList<>();

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
	
	public long getNextEntryPacketTime(){
		long min = Long.MAX_VALUE;
		for(Service service : services){
			if(min >= service.getNextPacketEntryTime()){
				min = service.getNextPacketEntryTime();
			}
		}
		return min;
	}
	
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
			return packet;
		}
		return null;
	}

	public int getId() {
		return id;
	}
}
