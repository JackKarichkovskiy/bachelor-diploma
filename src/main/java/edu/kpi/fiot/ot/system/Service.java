package edu.kpi.fiot.ot.system;

import edu.kpi.fiot.ot.system.generator.Generator;
import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

/**
 * Class that represents the service entity of the system.
 */
public class Service {
	
	/**
	 * The last time packet created.
	 */
	private long prevPacketEntryTime = 0;

	/**
	 * The next time packet will be created.
	 */
	private long nextPacketEntryTime;

	/**
	 * Average transfer time or process time of the service packet.
	 */
	private int averageTransferTime;

	/**
	 * Byte count in packets in this type of service.
	 */
	private int packetByteCount;
	
	/**
	 * If 0 - traffic is switched off, otherwise - switched on.
	 */
	private int trafficLevel = 1;

	/**
	 * Name of service.
	 */
	private String name;

	/**
	 * User that creates the service.
	 */
	private User user;
	
	/**
	 * Generator that identify the packet creation behaviour.
	 */
	private Generator gen;

	/**
	 * ID of service.
	 */
	private final int id;

	/**
	 * Auto incremented number.
	 */
	private static int gen_id = 1;
	
	public Service() {
		this.id = gen_id++;
	}
	
	public Service(String name, int averageTransferTime, int packetByteCount, Generator gen) {
		this();
		this.name = checkNotNull(name);
		this.gen = checkNotNull(gen);
		this.averageTransferTime = averageTransferTime;
		this.nextPacketEntryTime = gen.getNextInteger();
		this.packetByteCount = packetByteCount;
	}

	/**
	 * Generates new packet if traffic not equals to 0.
	 * 
	 * @return new packet.
	 */
	public Packet pollPacket() {
		Packet packet = null;
		if (trafficLevel != 0) {
			prevPacketEntryTime = nextPacketEntryTime;

			packet = new Packet();
			packet.setCreatingTime(nextPacketEntryTime);
			packet.setCalcLeft(averageTransferTime);
			packet.setByteCount(packetByteCount);
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

	public int getPacketByteCount() {
		return packetByteCount;
	}

	public void setPacketByteCount(int packetByteCount) {
		this.packetByteCount = packetByteCount;
	}
}
