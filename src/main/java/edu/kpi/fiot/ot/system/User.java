package edu.kpi.fiot.ot.system;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private List<Service> services = new ArrayList<>();

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}	
}
