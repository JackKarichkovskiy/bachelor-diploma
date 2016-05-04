package edu.kpi.fiot.ot.scheduler.preprocessor;

import edu.kpi.fiot.ot.scheduler.Packet;
import edu.kpi.fiot.ot.system.System;

public interface PreProcessor {
	Packet getNextPacket();
	
	long getNextEntryTime();
	
	void setSystem(System system);
}
