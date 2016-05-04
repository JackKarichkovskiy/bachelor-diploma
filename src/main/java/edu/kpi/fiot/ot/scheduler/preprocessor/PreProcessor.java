package edu.kpi.fiot.ot.scheduler.preprocessor;

import edu.kpi.fiot.ot.scheduler.Packet;

public interface PreProcessor {
	Packet getNextPacket();
	
	long getNextEntryTime();
}
