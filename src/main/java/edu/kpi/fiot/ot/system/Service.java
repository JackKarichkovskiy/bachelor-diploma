package edu.kpi.fiot.ot.system;

import edu.kpi.fiot.ot.system.generator.Generator;
import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

public class Service {
	
	private long prevPacketEntryTime = 0;

	private long nextPacketEntryTime;

	private int averageTransferTime;

	private int trafficLevel = 1;

	private String name;

	private User user;
	
	private Generator gen;

	private final int id;

	private static int gen_id = 1;
	
	public Service() {
		this.id = gen_id++;
	}
	
	public Service(String name, int averageTransferTime, Generator gen) {
		this();
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
			packet.setCreatingTime(prevPacketEntryTime);
			packet.setCalcLeft(averageTransferTime);
			packet.setService(this);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}
}
