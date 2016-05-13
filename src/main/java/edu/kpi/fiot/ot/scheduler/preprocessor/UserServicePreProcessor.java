package edu.kpi.fiot.ot.scheduler.preprocessor;

import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

import java.util.List;
import java.util.Set;

import edu.kpi.fiot.ot.scheduler.Scheduler;
import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.Service;
import edu.kpi.fiot.ot.system.System;
import edu.kpi.fiot.ot.system.User;

/**
 * Implementation of the preprocessor that works in two dimensions (users x services).
 */
public class UserServicePreProcessor implements PreProcessor {
	
	/**
	 * System with users and services.
	 */
	private System system;

	/**
	 * List of users in the system.
	 */
	private List<User> users;

	public UserServicePreProcessor() {
	}

	@Override
	public Packet getNextPacket() {
		Set<Service> systemServices = system.getServicesInSystem();
		long min = Long.MAX_VALUE;
		Service minService = null;
		for (User user : users) {
			List<Service> services = user.getServices();
			for (Service service : services) {
				if (!systemServices.contains(service)) {
					if (min >= service.getNextPacketEntryTime()) {
						min = service.getNextPacketEntryTime();
						minService = service;
					}
				}
			}
		}

		if (minService != null) {
			Packet packet = minService.pollPacket();
			User user = minService.getUser();
			packet.setUser(user);
			long calcLeft = packet.getCalcLeft();
			packet.setCalcLeft(calcLeft + user.getAverageTransferTime());
			return packet;
		}
		return null;
	}

	@Override
	public long getNextEntryTime() {
		Set<Service> systemServices = system.getServicesInSystem();
		long min = Long.MAX_VALUE;
		for (User user : users) {
			List<Service> services = user.getServices();
			for (Service service : services) {
				if (!systemServices.contains(service)) {
					if (min >= service.getNextPacketEntryTime()) {
						min = service.getNextPacketEntryTime();
					}
				}
			}
		}
		long currentTime = Scheduler.currentTime();
		return min < currentTime ? currentTime : min;
	}

	public System getSystem() {
		return system;
	}

	@Override
	public void setSystem(System system) {
		this.system = checkNotNull(system);
		this.users = system.getUsers();
	}

}
