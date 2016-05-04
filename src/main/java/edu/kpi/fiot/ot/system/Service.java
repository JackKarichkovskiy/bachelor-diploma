package edu.kpi.fiot.ot.system;

import edu.kpi.fiot.ot.system.generator.Generator;
import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

import edu.kpi.fiot.ot.scheduler.Packet;

public class Service {

	private long prevPacketEntryTime = 0;

	private long nextPacketEntryTime;

	private int averageTransferTime;

	private int trafficLevel = 1;

	private String name;

	private Generator gen;

	public Service(String name, int averageTransferTime, Generator gen) {
		this.name = checkNotNull(name);
		this.gen = checkNotNull(gen);
		this.averageTransferTime = averageTransferTime;
		this.nextPacketEntryTime = gen.getNextInteger();
	}

	public Packet pollPacket() {
		Packet packet = null;
		if (trafficLevel != 0) {
			prevPacketEntryTime = nextPacketEntryTime;

			packet = new Packet();
			packet.setEntryTime(prevPacketEntryTime);
			packet.setCalcLeft(averageTransferTime);
		}

		nextPacketEntryTime += gen.getNextInteger();
		return packet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Generator getGen() {
		return gen;
	}

	public void setGen(Generator gen) {
		this.gen = gen;
	}

	public long getNextPacketEntryTime() {
		return nextPacketEntryTime;
	}

	public void setNextPacketEntryTime(long nextPacketEntryTime) {
		this.nextPacketEntryTime = nextPacketEntryTime;
	}
}
