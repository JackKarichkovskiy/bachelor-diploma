package edu.kpi.fiot.ot.system;

import edu.kpi.fiot.ot.system.generator.Generator;
import static edu.kpi.fiot.ot.utils.ProjectUtils.checkNotNull;

public class Service {
	
	private String name;
	
	private Generator gen;
	
	public Service(String name, Generator gen){
		this.name = checkNotNull(name);
		this.gen = checkNotNull(gen);
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
}
