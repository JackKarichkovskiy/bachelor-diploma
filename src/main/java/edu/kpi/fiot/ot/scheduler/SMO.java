package edu.kpi.fiot.ot.scheduler;

public class SMO {

	private Queue queue;

	private Processor proc;

	private int taskCount;

	private double entryIntensity;

	//private EntryEvents entryEvents;
	
	private static long currentTime = 0;

	public SMO(int taskCount, double entryIntensity, Processor proc) {
		this.taskCount = taskCount;
		this.entryIntensity = entryIntensity;
		//this.entryEvents = getEntryEvents();
		this.proc = proc;
		this.queue = this.proc.queue;
	}

	public void go(){
		currentTime = 0;
		
		while(proc.obsoletePackets.size() + proc.donePackets.size() < taskCount){
//			long nextEntry = entryEvents.getNextEvent();
//			long nextCalc = proc.firstCalcEnd();
//			if(nextEntry < nextCalc){
//				currentTime = nextEntry;
//				Packet newPacket = Packet.getRandomPacket();
//				if(!proc.tryToAddNewPacket(newPacket))
//					queue.addPacket(newPacket);
//				entryEvents.currentEvent++;
//			}else{
//				//currentTime = nextCalc;
//				proc.solvePackets();
//			}
		}
	}
	
	public double getAverageWaitTime(){
		double sum = .0;
		for(Packet task : proc.obsoletePackets){
			sum += task.getWaitTime();
		}
		for(Packet task : proc.donePackets){
			sum += task.getWaitTime();
		}
		return sum / (proc.obsoletePackets.size() + proc.donePackets.size());
	}
	
	public long getWaitTime(){
		long sum = 0;
		for(Packet task : proc.obsoletePackets){
			sum += task.getWaitTime();
		}
		for(Packet task : proc.donePackets){
			sum += task.getWaitTime();
		}
		return sum;
	}
	
	public double getAverageCoreWaiting(){
		double sum = .0;
		for(Core core : proc.cores){
			sum += core.getWaitTime();
		}
		return sum / (proc.cores.length * SMO.currentTime());
	}
	
	public double getObsoletePercent(){
		return (double)proc.obsoletePackets.size() / taskCount;
	}
	
	public double getAverageQueueSize(){
		double result = (double)queue.getQueueSizeTime() / SMO.currentTime();
		return result;
	}
	
	public static long currentTime() {
		return currentTime;
	}

	public static void setCurrentTime(long newTime) {
		if (newTime >= currentTime) {
			currentTime = newTime;
		}else{
			throw new IllegalArgumentException();
		}
	}

//	private EntryEvents getEntryEvents() {
//		long[] entries = new long[taskCount];
//		for (int i = 0; i < entries.length; i++) {
//			entries[i] = (long)(i / entryIntensity);
//		}
//		EntryEvents entriesObj = new EntryEvents();
//		entriesObj.entries = entries;
//		return entriesObj;
//	}
}
