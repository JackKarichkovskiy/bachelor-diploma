package edu.kpi.fiot.ot.scheduler;

import edu.kpi.fiot.ot.scheduler.preprocessor.PreProcessor;

public class Scheduler {

	private Queue queue;

	private Processor proc;

	private PreProcessor preProc;

	private long timeLimit;

	// private EntryEvents entryEvents;

	private static long currentTime = 0;

	public Scheduler(long timeLimit, Processor proc, PreProcessor preProc) {
		this.timeLimit = timeLimit;
		// this.entryEvents = getEntryEvents();
		this.proc = proc;
		this.queue = this.proc.queue;
		this.preProc = preProc;
	}

	public void go() {
		currentTime = 0;

		while (currentTime < timeLimit) {
			long nextEntry = preProc.getNextEntryTime();
			long nextCalc = proc.firstCalcEnd();
			if (nextEntry < nextCalc) {
				currentTime = nextEntry;
				Packet newPacket = preProc.getNextPacket();
				if (!proc.tryToAddNewPacket(newPacket))
					queue.addPacket(newPacket);
			} else {
				// currentTime = nextCalc;
				proc.solvePackets();
			}
		}
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public Processor getProc() {
		return proc;
	}

	public void setProc(Processor proc) {
		this.proc = proc;
	}

	public double getChannelCapacity() {
		double donePacketsCount = (double) proc.donePackets.size();
		long currentTime = currentTime();
		return donePacketsCount / currentTime;
	}

	public double getAverageWaitTime() {
		double sum = .0;
		for (Packet task : proc.obsoletePackets) {
			sum += task.getWaitTime();
		}
		for (Packet task : proc.donePackets) {
			sum += task.getWaitTime();
		}
		return sum / (proc.obsoletePackets.size() + proc.donePackets.size());
	}

	public long getWaitTime() {
		long sum = 0;
		for (Packet task : proc.obsoletePackets) {
			sum += task.getWaitTime();
		}
		for (Packet task : proc.donePackets) {
			sum += task.getWaitTime();
		}
		return sum;
	}

	public double getAverageCoreWaiting() {
		double sum = .0;
		for (Core core : proc.cores) {
			sum += core.getWaitTime();
		}
		return sum / (proc.cores.length * Scheduler.currentTime());
	}

	public double getObsoletePercent() {
		return (double) proc.obsoletePackets.size() / (proc.obsoletePackets.size() + proc.donePackets.size());
	}

	public double getAverageQueueSize() {
		double result = (double) queue.getQueueSizeTime() / Scheduler.currentTime();
		return result;
	}

	public static long currentTime() {
		return currentTime;
	}

	public static void setCurrentTime(long newTime) {
		if (newTime >= currentTime) {
			currentTime = newTime;
		} else {
			throw new IllegalArgumentException();
		}
	}

	// private EntryEvents getEntryEvents() {
	// long[] entries = new long[taskCount];
	// for (int i = 0; i < entries.length; i++) {
	// entries[i] = (long)(i / entryIntensity);
	// }
	// EntryEvents entriesObj = new EntryEvents();
	// entriesObj.entries = entries;
	// return entriesObj;
	// }
}
