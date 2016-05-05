package edu.kpi.fiot.ot.scheduler.mt;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.scheduling.config.Task;

import edu.kpi.fiot.ot.scheduler.Packet;
import edu.kpi.fiot.ot.scheduler.Queue;
import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.User;

public class MTQueue extends Queue {

	private long lastEvent = 0;

	private PriorityQueue<Packet> queue = new PriorityQueue<>(new Comparator<Packet>() {

		@Override
		public int compare(Packet o1, Packet o2) {
			int result = (int) (o1.getCalcLeft() - o2.getCalcLeft());
			return result;
		}
	});

	@Override
	public Packet getNextPacket() {
		Packet packet = queue.poll();
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
		queue.add(packet);
		
		if (queue.size() > 1) {
			queueSizeTime += (Scheduler.currentTime() - lastEvent) * (queue.size() - 1);
			lastEvent = Scheduler.currentTime();
		}
	}

	@Override
	public Set<User> getUsersInQueue() {
		Set<User> users = new HashSet<>();
		for (Packet packet : queue) {
			users.add(packet.getUser());
		}
		return users;
	}

	@Override
	public Set<Service> getServicesInQueue() {
		Set<Service> services = new HashSet<>();
		for (Packet packet : queue) {
			services.add(packet.getService());
		}
		return services;
	}

}
