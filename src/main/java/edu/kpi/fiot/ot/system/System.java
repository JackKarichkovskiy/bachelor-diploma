package edu.kpi.fiot.ot.system;

import java.util.List;

public class System {
	
	private List<User> users;

	public int getUserCount(){
		return users.size();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
