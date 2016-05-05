package edu.kpi.fiot.ot.scheduler.rr;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Packet;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;

public class RRQueue extends Queue {

	private long lastEvent = 0;

	private LinkedList<Packet> queue = new LinkedList<>();

	@Override
	public Packet getNextPacket() {
		Packet packet = queue.pollLast();
		if (packet != null) {
			long waitTime = Scheduler.currentTime() - packet.getEntryTime();
			packet.setWaitTime(waitTime);
			queueSizeTime += (Scheduler.currentTime() - lastEvent) * (queue.size() + 1);
			lastEvent = Scheduler.currentTime();
		}

		return packet;
	}

	@Override
	public void addPacket(Packet packet) {
		System.out.println("[INFO]-" + Scheduler.currentTime() + ": " + packet + " adding to the queue");
		queue.addFirst(packet);
		queueSizeTime += (Scheduler.currentTime() - lastEvent) * (queue.size() - 1);
		lastEvent = Scheduler.currentTime();
	}

	@Override
	public Set<User> getUsersInQueue() {
		Set<User> users = new HashSet<>();
		for(Packet packet : queue){
			users.add(packet.getUser());
		}
		return users;
	}

	@Override
	public Set<Service> getServicesInQueue() {
		Set<Service> services = new HashSet<>();
		for(Packet packet : queue){
			services.add(packet.getService());
		}
		return services;
	}

}
