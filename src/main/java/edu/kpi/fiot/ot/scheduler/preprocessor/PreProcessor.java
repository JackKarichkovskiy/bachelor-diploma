package edu.kpi.fiot.ot.scheduler.preprocessor;

import edu.kpi.fiot.ot.system.Packet;
import edu.kpi.fiot.ot.system.System;

/**
 * Interface that presents preprocessor of scheduling algorithm.
 */
public interface PreProcessor {
	
	/**
	 * Method returns next down-coming packet from users in the system.
	 * 
	 * @return packet that comes from users.
	 */
	Packet getNextPacket();

	/**
	 * Method that returns the time of the next coming of packet.
	 * 
	 * @return the time of the next coming of packet.
	 */
	long getNextEntryTime();

	/**
	 * Sets the information about system for ability to monitor the users and its services.
	 * 
	 * @param system - system with users and services.
	 */
	void setSystem(System system);
}
